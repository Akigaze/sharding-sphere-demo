package demo.sharding.sphere.shardingjdbc.config.sharding.keygenerator;

import com.google.common.base.Preconditions;
import demo.sharding.sphere.shardingjdbc.config.sharding.constant.KeyGeneratorType;
import demo.sharding.sphere.shardingjdbc.config.sharding.constant.SnowflakePropertiesConstant;
import java.util.Calendar;
import java.util.Properties;
import lombok.SneakyThrows;
import org.apache.shardingsphere.core.strategy.keygen.TimeService;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

public class CustomizedKeyGenerator implements ShardingKeyGenerator {

  private Properties properties;

  public static final long EPOCH;

  public static final long SEQUENCE_BITS = 12L;

  public static final long WORKER_ID_BITS = 10L;

  public static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

  public static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

  public static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

  public static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

  public static final long WORKER_ID = 0;

  public static final int DEFAULT_VIBRATION_VALUE = 1;

  public static final int MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS = 10;

  private static TimeService timeService = new TimeService();

  private int sequenceOffset = -1;

  private long sequence;

  private long lastMilliseconds;

  static {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2016, Calendar.NOVEMBER, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    EPOCH = calendar.getTimeInMillis();
  }

  @Override
  public synchronized Comparable<?> generateKey() {
    long currentMilliseconds = timeService.getCurrentMillis();
    if (waitTolerateTimeDifferenceIfNeed(currentMilliseconds)) {
      currentMilliseconds = timeService.getCurrentMillis();
    }
    if (lastMilliseconds == currentMilliseconds) {
      if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
        currentMilliseconds = waitUntilNextTime(currentMilliseconds);
      }
    } else {
      vibrateSequenceOffset();
      sequence = sequenceOffset;
    }
    lastMilliseconds = currentMilliseconds;
    return ((currentMilliseconds - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (getWorkerId()
        << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
  }

  public static long keyToMillis(long key){
    return (key >> TIMESTAMP_LEFT_SHIFT_BITS) + EPOCH;
  }

  @SneakyThrows
  private boolean waitTolerateTimeDifferenceIfNeed(final long currentMilliseconds) {
    if (lastMilliseconds <= currentMilliseconds) {
      return false;
    }
    long timeDifferenceMilliseconds = lastMilliseconds - currentMilliseconds;
    Preconditions.checkState(timeDifferenceMilliseconds < getMaxTolerateTimeDifferenceMilliseconds(),
        "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds",
        lastMilliseconds, currentMilliseconds);
    Thread.sleep(timeDifferenceMilliseconds);
    return true;
  }

  private long getWorkerId() {
    long result = Long.parseLong(
        properties.getProperty(SnowflakePropertiesConstant.WORKER_ID.name(), String.valueOf(WORKER_ID)));
    Preconditions.checkArgument(result >= 0L && result < WORKER_ID_MAX_VALUE);
    return result;
  }

  private int getMaxVibrationOffset() {
    int result = Integer.parseInt(properties
        .getProperty(SnowflakePropertiesConstant.MAX_VIBRATION_OFFSET.name(),
            String.valueOf(DEFAULT_VIBRATION_VALUE)));
    Preconditions.checkArgument(result >= 0 && result <= SEQUENCE_MASK, "Illegal max vibration offset");
    return result;
  }

  private int getMaxTolerateTimeDifferenceMilliseconds() {
    return Integer.parseInt(properties.getProperty(SnowflakePropertiesConstant.MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS.name(),
            String.valueOf(MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS)));
  }

  private long waitUntilNextTime(final long lastTime) {
    long result = timeService.getCurrentMillis();
    while (result <= lastTime) {
      result = timeService.getCurrentMillis();
    }
    return result;
  }

  private void vibrateSequenceOffset() {
    sequenceOffset = sequenceOffset >= getMaxVibrationOffset() ? 0 : sequenceOffset + 1;
  }


  @Override
  public String getType() {
    return KeyGeneratorType.CUSTOMIZED.name();
  }

  @Override
  public Properties getProperties() {
    return properties;
  }

  @Override
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
