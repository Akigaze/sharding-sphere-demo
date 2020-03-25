package demo.sharding.jdbc3.algorithm;


import static demo.sharding.jdbc3.algorithm.SnowflakeShardingKeyGenerator.TIMESTAMP_LEFT_SHIFT_BITS;
import static demo.sharding.jdbc3.algorithm.SnowflakeShardingKeyGenerator.EPOCH;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import java.util.Collection;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IDPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

  @Override
  public java.lang.String doSharding(Collection<java.lang.String> availableTargetNames,
      PreciseShardingValue<Long> shardingValue) {
    Long id = shardingValue.getValue();
    long millis = (id >> TIMESTAMP_LEFT_SHIFT_BITS) + EPOCH;
    Date createdTime = new Date(millis);
    String suffix = CreatedTimeShardingFormatter.getTableSuffix(createdTime);
    String actualTable = shardingValue.getLogicTableName() + "_" + suffix;
    log.info("id: [{}], millis: [{}], table: [{}]", id, millis, actualTable);
    return availableTargetNames.contains(actualTable) ? actualTable
        : availableTargetNames.toArray(new String[0])[0];
  }
}
