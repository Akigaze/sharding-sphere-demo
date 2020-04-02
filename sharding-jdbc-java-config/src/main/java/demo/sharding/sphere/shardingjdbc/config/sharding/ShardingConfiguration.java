package demo.sharding.sphere.shardingjdbc.config.sharding;

import demo.sharding.sphere.shardingjdbc.ShardingProperties;
import demo.sharding.sphere.shardingjdbc.config.sharding.constant.KeyGeneratorType;
import demo.sharding.sphere.shardingjdbc.config.sharding.constant.SnowflakePropertiesConstant;
import demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.database.DefaultDataBasePreciseShardingAlgorithm;
import demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.database.DefaultDataBaseRangeShardingAlgorithm;
import demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.table.DefaultTableComplexShardingAlgorithm;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class ShardingConfiguration {

  private DataSourceConfiguration dataSourceConfig;

  private ShardingProperties properties;

  private TableRuleFactory tableRuleFactory;

  @Getter
  private DataSource shardingDataSource;

  private DataSource createDataSource() throws SQLException {
    Map<String, DataSource> dataSourceMap = new HashMap<>();
    dataSourceMap.put(dataSourceConfig.getMaster().getName(), dataSourceConfig.createMasterDatasource());
    dataSourceMap.put(dataSourceConfig.getSalve1().getName(), dataSourceConfig.createSalve1Datasource());

    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

    shardingRuleConfig.setDefaultKeyGeneratorConfig(this.getSnowFlakeKeyGeneratorConfig());
    shardingRuleConfig.setDefaultDataSourceName(dataSourceConfig.getMasterDataSourceName());
    shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
        new StandardShardingStrategyConfiguration("id", new DefaultDataBasePreciseShardingAlgorithm(), new DefaultDataBaseRangeShardingAlgorithm()));
    shardingRuleConfig.setDefaultTableShardingStrategyConfig(
        new ComplexShardingStrategyConfiguration("id, created_time", new DefaultTableComplexShardingAlgorithm()));

    TableRuleConfiguration cityTableRuleConfig = tableRuleFactory.createCityTableRule();
    TableRuleConfiguration eventTableRuleConfig = tableRuleFactory.createEventTableRule();

    shardingRuleConfig.getTableRuleConfigs().add(cityTableRuleConfig);
    shardingRuleConfig.getTableRuleConfigs().add(eventTableRuleConfig);


    DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, this.getShardingProperties());
    log.info(dataSource.getClass().getName());
    return dataSource;
  }

  private KeyGeneratorConfiguration getSnowFlakeKeyGeneratorConfig() {
    Properties props = new Properties();
    props.setProperty(SnowflakePropertiesConstant.WORKER_ID.getKey(), String.valueOf(properties.getWorkerId()));
    props.setProperty(SnowflakePropertiesConstant.MAX_VIBRATION_OFFSET.getKey(), String.valueOf(4095));
    props.setProperty(SnowflakePropertiesConstant.MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS.getKey(), String.valueOf(10));

    KeyGeneratorConfiguration configuration = new KeyGeneratorConfiguration(KeyGeneratorType.CUSTOMIZED.name(), "id", props);
    return configuration;
  }

  public Properties getShardingProperties() {
    Properties props = new Properties();
    props.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), String.valueOf(true));
    props.setProperty(ShardingPropertiesConstant.MAX_CONNECTIONS_SIZE_PER_QUERY.getKey(), String.valueOf(13));
    props.setProperty(ShardingPropertiesConstant.CHECK_TABLE_METADATA_ENABLED.getKey(), String.valueOf(false));
    return props;
  }
}
