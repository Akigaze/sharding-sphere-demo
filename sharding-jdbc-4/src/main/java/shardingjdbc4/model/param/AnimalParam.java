package shardingjdbc4.model.param;

import demo.sharding.sphere.common.model.base.InputConverter;
import java.util.Date;
import lombok.Data;
import shardingjdbc4.constant.Species;
import shardingjdbc4.model.entity.Animal;

@Data
public class AnimalParam implements InputConverter<Animal> {
  private String name;

  private Species species;

  private Boolean extinct;

  private Date recordDate;
}
