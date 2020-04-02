package demo.sharding.sphere.shardingjdbc.config.sharding.strategy.algorithm.table;

import java.util.Collection;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

public class DefaultTableComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      ComplexKeysShardingValue<String> shardingValue) {
    return null;
  }
}
