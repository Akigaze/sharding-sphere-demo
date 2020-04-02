package demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.database;

import java.util.Collection;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

public class DefaultDataBaseRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      RangeShardingValue<Long> shardingValue) {
    return null;
  }
}
