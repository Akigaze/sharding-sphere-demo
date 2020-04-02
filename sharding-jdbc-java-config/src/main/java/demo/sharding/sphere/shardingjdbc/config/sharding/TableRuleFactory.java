package demo.sharding.sphere.shardingjdbc.config.sharding;

import demo.sharding.sphere.shardingjdbc.ShardingProperties;
import demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.table.MonthlyComplexShardingAlgorithm;
import demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.table.PreciseModuloShardingAlgorithm;
import demo.sharding.sphere.shardingjdbc.config.sharding.util.ShardingUtil;
import lombok.AllArgsConstructor;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TableRuleFactory {

  private ShardingProperties properties;

  public TableRuleConfiguration createCityTableRule(String ...dataSourceNames) {
    TableRuleConfiguration rule = new TableRuleConfiguration("cities", "master.cities, master.cities_${0..11}");
    StandardShardingStrategyConfiguration strategyConfiguration = new StandardShardingStrategyConfiguration(
      "id", new PreciseModuloShardingAlgorithm(12));
    rule.setTableShardingStrategyConfig(strategyConfiguration);
    return rule;
  }

  public TableRuleConfiguration createEventTableRule(String ...dataSourceNames) {
    TableRuleConfiguration rule = new TableRuleConfiguration("events", "master.events, master.events_20_${3..5}");
    ShardingStrategyConfiguration strategyConfiguration = new ComplexShardingStrategyConfiguration(
      ShardingUtil.makeShardingColumns(MonthlyComplexShardingAlgorithm.getShardingColumns()),
      new MonthlyComplexShardingAlgorithm(properties.getPattern()));
    rule.setTableShardingStrategyConfig(strategyConfiguration);
    return rule;
  }
}
