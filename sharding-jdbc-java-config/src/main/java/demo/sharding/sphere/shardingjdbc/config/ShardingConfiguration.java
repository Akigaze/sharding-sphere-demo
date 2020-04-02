package demo.sharding.sphere.shardingjdbc.config;

import demo.sharding.sphere.shardingjdbc.ShardingProperties;
import demo.sharding.sphere.shardingjdbc.config.sharding.DataSourceConfiguration;
import demo.sharding.sphere.shardingjdbc.config.sharding.constant.KeyGeneratorType;
import demo.sharding.sphere.shardingjdbc.config.sharding.ShardingStrategyFactory;
import demo.sharding.sphere.shardingjdbc.config.sharding.constant.SnowflakePropertiesConstant;
import demo.sharding.sphere.shardingjdbc.config.sharding.TableRuleFactory;
import demo.sharding.sphere.shardingjdbc.config.sharding.strategy.algorithm.database.DefaultDataBasePreciseShardingAlgorithm;
import demo.sharding.sphere.shardingjdbc.config.sharding.strategy.algorithm.database.DefaultDataBaseRangeShardingAlgorithm;
import demo.sharding.sphere.shardingjdbc.config.sharding.strategy.algorithm.table.DefaultTableComplexShardingAlgorithm;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Slf4j
@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShardingConfiguration {

  private DataSourceConfiguration dataSourceConfig;

  private ShardingProperties properties;

  private ShardingStrategyFactory strategyFactory;

  private TableRuleFactory tableRuleFactory;

  @Bean
  @Primary
  public DataSource shardingDataSource() throws SQLException {
    return this.buildDataSource();
  }

  private DataSource buildDataSource() throws SQLException {
    Map<String, DataSource> dataSourceMap = new HashMap<>();
    dataSourceMap.put(dataSourceConfig.getMaster().getName(), dataSourceConfig.createMasterDatasource());
    dataSourceMap.put(dataSourceConfig.getSalve1().getName(), dataSourceConfig.createSalve1Datasource());

    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

    shardingRuleConfig.setDefaultKeyGeneratorConfig(this.getSnowFlakeKeyGeneratorConfig());
    shardingRuleConfig.setDefaultDataSourceName(dataSourceConfig.getMasterDataSourceName());
    shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
        strategyFactory.createShardingStrategy("id", new DefaultDataBasePreciseShardingAlgorithm(), new DefaultDataBaseRangeShardingAlgorithm()));
    shardingRuleConfig.setDefaultTableShardingStrategyConfig(
        strategyFactory.createShardingStrategy("id, rec_cre_dt_utc", new DefaultTableComplexShardingAlgorithm()));

    TableRuleConfiguration cityTableRuleConfig = tableRuleFactory.createCityTableRule();
    TableRuleConfiguration goodTableRuleConfig = tableRuleFactory.createGoodTableRule();
    TableRuleConfiguration orderTableRuleConfig = tableRuleFactory.createOrderTableRule();
    TableRuleConfiguration eventTableRuleConfig = tableRuleFactory.createEventTableRule();

    shardingRuleConfig.getTableRuleConfigs().add(cityTableRuleConfig);
    shardingRuleConfig.getTableRuleConfigs().add(goodTableRuleConfig);
    shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);


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
