package demo.sharding.sphere.shardingjdbc.model.entity;


import demo.sharding.sphere.common.model.base.BaseEntity;
import demo.sharding.sphere.shardingjdbc.constant.enums.Scale;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@DynamicInsert
@Table(name = "cities")
@EqualsAndHashCode(callSuper = false)
public class City extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String country;

  private Long population;

  private Boolean existed;

  @Enumerated(EnumType.STRING)
  private Scale scale;

  @Temporal(TemporalType.TIMESTAMP)
  private Date establishedTime;

}

