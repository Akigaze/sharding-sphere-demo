package demo.sharding.jdbc.yml.config.sharding.algorithm;


import static demo.sharding.jdbc.yml.config.sharding.keygenerator.SnowflakeShardingKeyGenerator.EPOCH;
import static demo.sharding.jdbc.yml.config.sharding.keygenerator.SnowflakeShardingKeyGenerator.TIMESTAMP_LEFT_SHIFT_BITS;

import demo.sharding.jdbc.yml.ShardingJdbcYmlConfigProperties;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IDPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

  private static SimpleDateFormat suffixFormat;

  @Override
  public String doSharding(Collection<String> availableTargetNames,
      PreciseShardingValue<Long> shardingValue) {
    Long id = shardingValue.getValue();
    long millis = (id >> TIMESTAMP_LEFT_SHIFT_BITS) + EPOCH;
    Date createdTime = new Date(millis);
    String suffix = getTableSuffix(createdTime);
    String actualTable = shardingValue.getLogicTableName() + "_" + suffix;
    log.info("table: [{}], id: [{}], createdTime: [{}]", actualTable, id, createdTime);
    log.debug("doSharding createdTime milliseconds: [{}]", createdTime.getTime());
    return availableTargetNames.contains(actualTable) ? actualTable
        : availableTargetNames.toArray(new String[0])[0];
  }

  public static String getTableSuffix(Date date) {
    return getSuffixFormat().format(date);
  }

  public static SimpleDateFormat getSuffixFormat() {
    if (suffixFormat == null) {
      suffixFormat = new SimpleDateFormat(ShardingJdbcYmlConfigProperties.getShardingConfig().getCreatedTimeShardingPattern());
    }
    return suffixFormat;
  }
}
