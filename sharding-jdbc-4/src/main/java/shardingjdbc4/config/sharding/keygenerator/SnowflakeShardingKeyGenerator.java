package shardingjdbc4.config.sharding.keygenerator;

import com.google.common.base.Preconditions;
import java.util.Calendar;
import java.util.Properties;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.strategy.keygen.TimeService;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import shardingjdbc4.ShardingJdbc4Properties;

// copy from org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator
@Slf4j
public final class SnowflakeShardingKeyGenerator implements ShardingKeyGenerator {

  public static final long EPOCH;

  private static final long SEQUENCE_BITS = 12L;

  private static final long WORKER_ID_BITS = 10L;

  private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

  private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

  public static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

  private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

  private static final long WORKER_ID = 0;

  private static final int DEFAULT_VIBRATION_VALUE = 1;

  private static final int MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS = 10;

  private static TimeService timeService = new TimeService();

  @Setter
  private Properties properties;

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

  public String getType() {
    return "SNOWFLAKE_PLUS";
  }

  @Override
  public synchronized Long generateKey() {
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
    long key = ((currentMilliseconds - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (getWorkerId()
        << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    log.debug("generate key milliseconds: [{}], key: [{}]", currentMilliseconds, key);
    return key;
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
    long result = Long.parseLong(getProperties().getProperty("worker.id", String.valueOf(WORKER_ID)));
    Preconditions.checkArgument(result >= 0L && result < WORKER_ID_MAX_VALUE);
    return result;
  }

  private int getMaxVibrationOffset() {
    int result = Integer
        .parseInt(
            getProperties().getProperty("max.vibration.offset", String.valueOf(DEFAULT_VIBRATION_VALUE)));
    Preconditions.checkArgument(result >= 0 && result <= SEQUENCE_MASK, "Illegal max vibration offset");
    return result;
  }

  private int getMaxTolerateTimeDifferenceMilliseconds() {
    return Integer.valueOf(getProperties().getProperty("max.tolerate.time.difference.milliseconds",
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

  public Properties getProperties() {
    if (properties == null) {
      properties = ShardingJdbc4Properties.getShardingConfig().getKeyGeneratorProps();
    }
    return properties;
  }

}
