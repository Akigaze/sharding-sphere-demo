package shardingjdbc4.model.entity;


import demo.sharding.sphere.common.model.base.BaseEntity;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import shardingjdbc4.constant.Category;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@DynamicInsert
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Category category;

  private Boolean deleted;

  @Temporal(TemporalType.TIMESTAMP)
  private Date completedTime;
}
