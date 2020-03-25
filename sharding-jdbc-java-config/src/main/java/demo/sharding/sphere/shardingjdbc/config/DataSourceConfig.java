package demo.sharding.sphere.shardingjdbc.config;

import demo.sharding.sphere.shardingjdbc.ShardingJdbcProperties;
import demo.sharding.sphere.shardingjdbc.config.TableConfig.ShardingTable;
import demo.sharding.sphere.shardingjdbc.config.strategy.ShardingStrategyManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Slf4j
@Configuration
public class DataSourceConfig {

  private DatabaseConfig databaseConfig;

  private TableConfig tableConfig;

  private ShardingJdbcProperties properties;

  private ShardingStrategyManager strategyManager;

  @Autowired
  public DataSourceConfig(DatabaseConfig databaseConfig, TableConfig tableConfig,
      ShardingJdbcProperties properties, ShardingStrategyManager strategyManager) {
    this.databaseConfig = databaseConfig;
    this.tableConfig = tableConfig;
    this.properties = properties;
    this.strategyManager = strategyManager;
  }

  @Bean
  @Primary
  public DataSource shardingDataSource() throws SQLException {
    return this.buildDataSource();
  }

  private DataSource buildDataSource() throws SQLException {
    Map<String, DataSource> dataSourceMap = new HashMap<>();
    dataSourceMap.put(databaseConfig.getSharding0().getName(), databaseConfig.createDatasource0());

//    dataSourceMap.put(databaseConfig.getSharding1().getName(), databaseConfig.createDatasource1());

    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

    TableRuleConfiguration goodsTableRuleConfig = this.createTableRuleConfiguration(tableConfig.getGoods());
    TableRuleConfiguration ordersTableRuleConfig = this.createTableRuleConfiguration(tableConfig.getOrders());
    shardingRuleConfig.getTableRuleConfigs().add(goodsTableRuleConfig);
    shardingRuleConfig.getTableRuleConfigs().add(ordersTableRuleConfig);

    shardingRuleConfig.setDefaultDataSourceName(properties.getMainDatabase());
    shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
        strategyManager.createShardingStrategy(properties.getDefaultDatabaseShardingStrategy()));
    shardingRuleConfig.setDefaultTableShardingStrategyConfig(
        strategyManager.createShardingStrategy(properties.getDefaultTableShardingStrategy()));

    DataSource dataSource = ShardingDataSourceFactory
        .createDataSource(dataSourceMap, shardingRuleConfig, this.getShardingDataSourceProperties());
    log.info(dataSource.getClass().getName());
    return dataSource;
  }

  private Properties getShardingDataSourceProperties() {
    Properties props = new Properties();
    props.setProperty("sql.show", String.valueOf(properties.getShowSql()));
    return props;
  }


  private TableRuleConfiguration createTableRuleConfiguration(ShardingTable table) {
    TableRuleConfiguration ruleConfig = new TableRuleConfiguration(table.getLogicTable(),
        table.getActualDataNodes());
    ruleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration(
        table.getKeyGeneratorType().name(), table.getKeyGeneratorColumn()));
    return ruleConfig;
  }
}
