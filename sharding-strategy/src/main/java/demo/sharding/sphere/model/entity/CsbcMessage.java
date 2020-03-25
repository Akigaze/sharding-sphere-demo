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
@DynamicInsert
@DynamicUpdate
@Table(name = "csbc_msg")
@EqualsAndHashCode(callSuper = false)
public class CsbcMessage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String businessKey;

  private String type;

  private String eventId;

  private String assetId;

  private String sender;

  private String receiver;

  private String receiverRoles;

  private Long version;

  private String content;

  private String exceptionLog;

  private String processState;


}
