package demo.sharding.sphere.shardingjdbc.model.entity;

import demo.sharding.sphere.common.model.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;


@Data
@Entity
@DynamicInsert
@Table(name = "orders")
@EqualsAndHashCode(callSuper = false)
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long goodId;

  private Integer size = -1;
}
