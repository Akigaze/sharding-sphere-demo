package demo.sharding.sphere.shardingjdbc.controller;

import demo.sharding.sphere.shardingjdbc.ShardingJdbcProperties;
import demo.sharding.sphere.shardingjdbc.config.DatabaseConfig;
import demo.sharding.sphere.shardingjdbc.config.TableConfig;
import java.util.Objects;
import javax.swing.text.TabableView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sharding/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

  private final ShardingJdbcProperties properties;

  private final DatabaseConfig databaseConfig;

  private final TableConfig tableConfig;


  @GetMapping("properties")
  public Object getShardingJdbcProperties(){
    return properties;
  }

  @GetMapping("database")
  public Object getDatabaseConfig(){
    return databaseConfig;
  }

  @GetMapping("table")
  public Object getTableConfig(){
    return tableConfig;
  }
}
