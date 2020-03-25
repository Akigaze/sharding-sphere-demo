package demo.sharding.jdbc3;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "jdbc3")
public class ShardingJdbc3Properties {

  private static String createdTimeShardingRule;

  private static Long workerId;

  public static String getCreatedTimeShardingRule() {
    return createdTimeShardingRule;
  }

  @Value("${jdbc3.createdTimeShardingRule:yy_M_d}")
  public void setCreatedTimeShardingRule(String createdTimeShardingRule) {
    ShardingJdbc3Properties.createdTimeShardingRule = createdTimeShardingRule;
  }

  public static Long getWorkerId() {
    return workerId;
  }

  @Value("${jdbc3.workerId:0}")
  public void setWorkerId(Long workerId) {
    ShardingJdbc3Properties.workerId = workerId;
  }
}
