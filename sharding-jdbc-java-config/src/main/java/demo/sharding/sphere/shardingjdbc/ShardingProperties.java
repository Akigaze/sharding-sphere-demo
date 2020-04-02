package demo.sharding.sphere.shardingjdbc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "java.sharding")
public class ShardingProperties {
  private boolean enabled;

  private int workerId;
}
