package shardingjdbc4.model.param;

import demo.sharding.sphere.common.model.base.InputConverter;
import java.util.Date;
import lombok.Data;
import shardingjdbc4.constant.Category;
import shardingjdbc4.model.entity.Event;

@Data
public class EventParam implements InputConverter<Event> {

  private String name;

  private Category category;

  private Boolean deleted;

  private Date completedTime;
}
