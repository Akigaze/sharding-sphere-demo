package demo.strategy.hint;

import java.util.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "hint.sharding")
public class ShardingConfiguration {

  private boolean enabled = false;

  private String yamlFilePath;

  private String databaseNodeName;
}
