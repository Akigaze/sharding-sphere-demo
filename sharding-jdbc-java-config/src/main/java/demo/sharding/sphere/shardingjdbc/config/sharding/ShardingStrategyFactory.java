package demo.sharding.sphere.shardingjdbc.config.sharding;

import lombok.AllArgsConstructor;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.ShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShardingStrategyFactory {

  public ShardingStrategyConfiguration createShardingStrategy(String shardingColumn,
      PreciseShardingAlgorithm<?> preciseShardingAlgorithm,
      RangeShardingAlgorithm<?> rangeShardingAlgorithm) {
    return null;
  }

  public ShardingStrategyConfiguration createShardingStrategy(String shardingColumns,
      ShardingAlgorithm shardingAlgorithm) {
    return null;
  }
}
