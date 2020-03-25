package demo.sharding.sphere.model.entity;

import demo.sharding.sphere.common.model.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "csbc_msg_process")
@EqualsAndHashCode(callSuper = false)
public class CsbcMessageProcess extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String businessKey;

  private Long csbcId;

  private String type;

  private String eventId;

  private String assetId;

  private Integer version;

  private String exceptionLog;

  private String processState;
}
