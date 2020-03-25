package demo.sharding.sphere.shardingjdbc.model.param;

import demo.sharding.sphere.common.model.base.InputConverter;
import demo.sharding.sphere.shardingjdbc.model.entity.Good;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodParam implements InputConverter<Good> {

  private String name;

  private Integer category;
}
