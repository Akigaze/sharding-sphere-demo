package demo.sharding.sphere.shardingjdbc.model.param;


import demo.sharding.sphere.common.model.base.InputConverter;
import demo.sharding.sphere.shardingjdbc.constant.enums.Scale;
import demo.sharding.sphere.shardingjdbc.model.entity.City;
import java.util.Date;
import lombok.Data;


@Data
public class CityParam implements InputConverter<City> {

  private String name;

  private String country;

  private Long population;

  private Boolean existed;

  private Scale scale;

  private Date establishedTime;
}
