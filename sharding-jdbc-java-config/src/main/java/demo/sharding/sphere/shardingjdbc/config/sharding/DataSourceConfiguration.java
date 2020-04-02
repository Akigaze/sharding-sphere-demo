package demo.sharding.sphere.shardingjdbc.config.sharding;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.Assert;

@Data
@ConfigurationProperties(prefix = "java.sharding.datasource")
public class DataSourceConfiguration {

  @Data
  public static class DataSourceProperties {

    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private String name;
    private boolean enabled = false;
  }

  private DataSourceProperties master;

  private DataSourceProperties salve1;

  private DataSourceProperties salve2;

  public DataSource createMasterDatasource() {
    return this.createDataSource(this.master);
  }

  public DataSource createSalve1Datasource() {
    return this.createDataSource(this.salve1);
  }

  public DataSource createSalve2Datasource() {
    return this.createDataSource(this.salve2);
  }

  public String getMasterDataSourceName(){
    return master.name;
  }

  public DataSource createDataSource(DataSourceProperties properties) {
    Assert.isTrue(properties.enabled, "DataSource is not enabled, could not create: " + properties.name);
    DataSource dataSource = DataSourceBuilder.create()
        .url(properties.url)
        .driverClassName(properties.driverClassName)
        .username(properties.username)
        .password(properties.password)
        .type(HikariDataSource.class)
        .build();
    return dataSource;
  }



}
