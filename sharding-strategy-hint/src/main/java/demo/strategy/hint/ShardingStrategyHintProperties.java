package demo.strategy.hint;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties({ShardingConfiguration.class})
@ConfigurationProperties(prefix = "shardingjdbc4")
public class ShardingStrategyHintProperties {

  private static ShardingConfiguration shardingConfig;

  public static ShardingConfiguration getShardingConfig() {
    return shardingConfig;
  }

  @Autowired
  public void setShardingConfig(ShardingConfiguration shardingConfig) {
    ShardingStrategyHintProperties.shardingConfig = shardingConfig;
  }

}
