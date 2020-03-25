package demo.sharding.jdbc.yml;

import java.util.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yml.sharding")
public class ShardingConfiguration {

  private boolean enabled = false;

  private String yamlFilePath;

  private String databaseNodeName;

  private String createdTimeShardingPattern;

  private Properties keyGeneratorProps = new Properties();
}
