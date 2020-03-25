package demo.sharding.sphere.shardingjdbc.config;

import javax.sql.DataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Data
@ConfigurationProperties(prefix = "sharding.database")
public class DatabaseConfig {

  @Data
  static class ShardingDatabase {

    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private String name;
    private Boolean enabled = true;
  }

  private ShardingDatabase sharding0;

  private ShardingDatabase sharding1;

  public DataSource createDatasource0() {
    return this.createDataSource(this.sharding0);
  }


  public DataSource createDatasource1() {
    return this.createDataSource(this.sharding1);
  }

  private DataSource createDataSource(ShardingDatabase database) {
    if (!database.enabled) {
      return null;
    }
    DataSource dataSource = DataSourceBuilder.create()
        .url(database.url)
        .driverClassName(database.driverClassName)
        .username(database.username)
        .password(database.password)
        .build();
    return dataSource;
  }

}
