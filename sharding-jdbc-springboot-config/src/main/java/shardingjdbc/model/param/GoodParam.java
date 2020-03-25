package shardingjdbc.model.param;


import demo.sharding.sphere.common.model.base.InputConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shardingjdbc.model.entity.Good;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodParam implements InputConverter<Good> {
  private String name;

  private Integer category;
}
