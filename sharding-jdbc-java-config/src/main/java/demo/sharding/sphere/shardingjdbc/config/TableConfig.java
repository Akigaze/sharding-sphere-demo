package demo.sharding.sphere.shardingjdbc.config;

import demo.sharding.sphere.shardingjdbc.config.strategy.KeyGeneratorType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sharding.tables")
public class TableConfig {

  @Data
  public static class ShardingTable {

    private String name;

    private String logicTable;

    private String actualDataNodes;

    private KeyGeneratorType keyGeneratorType = KeyGeneratorType.SNOWFLAKE;

    private String keyGeneratorColumn = "id";
  }

  private ShardingTable goods;

  private ShardingTable orders;
}
