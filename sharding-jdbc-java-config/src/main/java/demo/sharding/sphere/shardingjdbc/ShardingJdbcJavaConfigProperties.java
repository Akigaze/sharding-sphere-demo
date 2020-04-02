package demo.sharding.sphere.shardingjdbc;

import demo.sharding.sphere.shardingjdbc.config.sharding.DataSourceConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties({DataSourceConfiguration.class, ShardingProperties.class})
@ConfigurationProperties(prefix = "java")
public class ShardingJdbcJavaConfigProperties {

}
