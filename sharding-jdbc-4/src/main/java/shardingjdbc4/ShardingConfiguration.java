package shardingjdbc4;

import java.util.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "shardingjdbc4.sharding")
public class ShardingConfiguration {

  private boolean enabled = false;

  private String yamlFilePath;

  private String databaseNodeName;

  private String createdTimeShardingPattern;

  private Properties keyGeneratorProps = new Properties();
}
