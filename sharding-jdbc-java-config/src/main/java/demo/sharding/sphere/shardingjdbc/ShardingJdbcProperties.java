package demo.sharding.sphere.shardingjdbc;

import demo.sharding.sphere.shardingjdbc.config.DatabaseConfig;
import demo.sharding.sphere.shardingjdbc.config.TableConfig;
import demo.sharding.sphere.shardingjdbc.config.strategy.ShardingStrategyConfigurationType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties({DatabaseConfig.class, TableConfig.class})
@ConfigurationProperties(prefix = "sharding")
public class ShardingJdbcProperties {

  @Data
  public static class ShardingStrategy {

    private String column = "id";

    private ShardingStrategyConfigurationType type = ShardingStrategyConfigurationType.inline;

    private String algorithm;

    private String preciseAlgorithm;

    private String rangeAlgorithm;
  }

  private String mainDatabase;

  private Boolean showSql = false;

  private ShardingStrategy defaultDatabaseShardingStrategy;

  private ShardingStrategy defaultTableShardingStrategy;
}
