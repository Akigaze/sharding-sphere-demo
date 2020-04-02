package demo.sharding.sphere.shardingjdbc.config.sharding.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StringColumnWrapper implements ColumnWrapper<String> {

  private String columnName;

  private String value;

}
