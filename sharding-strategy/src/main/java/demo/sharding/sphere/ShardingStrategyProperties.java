package demo.sharding.sphere;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "sharding")
public class ShardingStrategyProperties {

  private static String createdTimeShardingRule;

  public static String getCreatedTimeShardingRule() {
    return createdTimeShardingRule;
  }

  @Value("${sharding.createdTimeShardingRule:yy_M_d}")
  public void setCreatedTimeShardingRule(String createdTimeShardingRule) {
    ShardingStrategyProperties.createdTimeShardingRule = createdTimeShardingRule;
  }
}
