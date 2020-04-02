package demo.sharding.sphere.shardingjdbc.config.sharding.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SnowflakePropertiesConstant {
  WORKER_ID("worker.id", String.valueOf(0), long.class),
  MAX_VIBRATION_OFFSET("max.vibration.offset", String.valueOf(1), int.class),
  MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS("max.tolerate.time.difference.milliseconds", String.valueOf(0), int.class);

  private final String key;

  private final String defaultValue;

  private final Class<?> type;

}
