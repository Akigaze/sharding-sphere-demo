package shardingjdbc.model.param;

import demo.sharding.sphere.common.model.base.InputConverter;
import lombok.Data;
import shardingjdbc.model.entity.User;

@Data
public class UserParam implements InputConverter<User> {

  private String username;

  private String password;

  private Boolean deleted = false;
}
