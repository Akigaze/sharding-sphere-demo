package demo.sharding.sphere.shardingjdbc.controller;

import demo.sharding.sphere.shardingjdbc.ShardingJdbcJavaConfigProperties;
import demo.sharding.sphere.shardingjdbc.config.sharding.DataSourceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sharding/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

  private final ShardingJdbcJavaConfigProperties properties;

  private final DataSourceConfiguration dataSourceConfiguration;


  @GetMapping("properties")
  public Object getShardingJdbcProperties(){
    return properties;
  }

  @GetMapping("database")
  public Object getDataSourceConfiguration(){
    return dataSourceConfiguration;
  }

}
