package shardingjdbc4;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties({ShardingConfiguration.class})
@ConfigurationProperties(prefix = "shardingjdbc4")
public class ShardingJdbc4Properties {

  private static ShardingConfiguration shardingConfig;

  public static ShardingConfiguration getShardingConfig() {
    return shardingConfig;
  }

  @Autowired
  public void setShardingConfig(ShardingConfiguration shardingConfig) {
    ShardingJdbc4Properties.shardingConfig = shardingConfig;
  }

}
