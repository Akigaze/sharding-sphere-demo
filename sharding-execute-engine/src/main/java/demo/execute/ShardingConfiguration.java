package demo.execute;

import java.util.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "execute.sharding")
public class ShardingConfiguration {

  private boolean enabled = false;

  private String yamlFilePath;

  private String databaseNodeName;
}
