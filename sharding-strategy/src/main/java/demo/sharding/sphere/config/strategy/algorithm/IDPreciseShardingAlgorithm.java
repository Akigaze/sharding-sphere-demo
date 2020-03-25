package demo.sharding.sphere.config.strategy.algorithm;

import static demo.sharding.sphere.config.strategy.algorithm.SnowflakeShardingKeyGenerator.EPOCH;
import static demo.sharding.sphere.config.strategy.algorithm.SnowflakeShardingKeyGenerator.TIMESTAMP_LEFT_SHIFT_BITS;

import java.util.Collection;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

@Slf4j
public class IDPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

  @Override
  public String doSharding(Collection<String> availableTargetNames,
      PreciseShardingValue<Long> shardingValue) {
    Long id = shardingValue.getValue();
    long millis = (id >> TIMESTAMP_LEFT_SHIFT_BITS) + EPOCH;
    Date createdTime = new Date(millis);
    String suffix = CreatedTimeShardingFormatter.getTableSuffix(createdTime);
    String actualTable = shardingValue.getLogicTableName() + "_" + suffix;
    log.info("id: [{}], millis: [{}], table: [{}]", id, millis, actualTable);
    return availableTargetNames.contains(actualTable) ? actualTable
        : IterableUtils.first(availableTargetNames);
  }
}
