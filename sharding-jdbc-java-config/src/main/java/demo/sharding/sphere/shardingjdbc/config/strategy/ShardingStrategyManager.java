package demo.sharding.sphere.shardingjdbc.config.strategy;

import demo.sharding.sphere.shardingjdbc.ShardingJdbcProperties.ShardingStrategy;
import demo.sharding.sphere.shardingjdbc.config.strategy.algorithm.TimingShardingTableAlgorithm;
import demo.sharding.sphere.shardingjdbc.config.strategy.algorithm.TimingShardingTableAlgorithm.TimingUnit;
import lombok.AllArgsConstructor;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.NoneShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShardingStrategyManager {

  private ApplicationContext context;

  public ShardingStrategyConfiguration createShardingStrategy(ShardingStrategy strategy) {
    Assert.notNull(strategy, "strategy should not be null!");

    ShardingStrategyConfigurationType strategyType = strategy.getType();
    if (ShardingStrategyConfigurationType.inline == strategyType) {
      return createInlineShardingStrategyConfiguration(strategy);
    } else if (ShardingStrategyConfigurationType.standard == strategyType) {
      return this.createStandardShardingStrategyConfiguration(strategy);
    } else if (ShardingStrategyConfigurationType.none == strategyType) {
      return new NoneShardingStrategyConfiguration();
    }
    throw new UnsupportedOperationException(strategy.getType() + " is not supported type!");
  }

  private StandardShardingStrategyConfiguration createStandardShardingStrategyConfiguration(
      ShardingStrategy strategy) {
    PreciseShardingAlgorithm<?> preciseAlgorithm = this
        .createPreciseShardingAlgorithm(strategy.getPreciseAlgorithm());
    RangeShardingAlgorithm<?> rangeAlgorithm = this
        .createRangeShardingAlgorithm(strategy.getPreciseAlgorithm());
    return new StandardShardingStrategyConfiguration(strategy.getColumn(), preciseAlgorithm, rangeAlgorithm);
  }

  private ShardingStrategyConfiguration createInlineShardingStrategyConfiguration(ShardingStrategy strategy) {
    return new InlineShardingStrategyConfiguration(strategy.getColumn(), strategy.getAlgorithm());
  }

  private RangeShardingAlgorithm<?> createRangeShardingAlgorithm(String rangeAlgorithm) {
    try {
      RangeShardingAlgorithm<?> algorithm = context.getBean(rangeAlgorithm, RangeShardingAlgorithm.class);
      return algorithm;
    } catch (Exception e) {
      return null;
    }
  }

  private PreciseShardingAlgorithm<?> createPreciseShardingAlgorithm(String algorithmName) {
    Assert.notNull(algorithmName, "precise-algorithm should be not null!");
    PreciseShardingAlgorithm<?> algorithm = context.getBean(algorithmName, PreciseShardingAlgorithm.class);
    if (algorithm instanceof TimingShardingTableAlgorithm) {
      TimingShardingTableAlgorithm timingAlgorithm = (TimingShardingTableAlgorithm) algorithm;
      timingAlgorithm.setBy(TimingUnit.MINUTE);
      timingAlgorithm.setInterval(2);
    }
    return algorithm;
  }

}
