package demo.sharding.sphere.shardingjdbc.config.sharding;

import demo.sharding.sphere.shardingjdbc.config.sharding.strategy.algorithm.table.CreatedTimeComplexShardingAlgorithm;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.springframework.stereotype.Component;

@Component
public class TableRuleFactory {

  public TableRuleConfiguration createCityTableRule(String ...dataSourceNames) {
    TableRuleConfiguration rule = new TableRuleConfiguration("cities", "master.cities, master.cities_${0..11}");

    return rule;
  }

  public TableRuleConfiguration createOrderTableRule() {
    TableRuleConfiguration rule = new TableRuleConfiguration("orders", "master.orders, master.orders_${0..3}");

    return rule;
  }

  public TableRuleConfiguration createGoodTableRule() {
    TableRuleConfiguration rule = new TableRuleConfiguration("goods", "master.goods, master.goods_${0..3}");

    return rule;
  }

  public TableRuleConfiguration createEventTableRule() {
    TableRuleConfiguration rule = new TableRuleConfiguration("events", "master.events, master.events_20_${3..5}");
    rule.setTableShardingStrategyConfig(new CreatedTimeComplexShardingAlgorithm());
    return rule;
  }
}
