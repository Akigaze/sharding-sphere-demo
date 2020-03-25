package demo.sharding.jdbc3.model.entity;

import demo.sharding.jdbc3.constant.MessageType;
import demo.sharding.sphere.common.model.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "msg")
@EqualsAndHashCode(callSuper = false)
public class CsbcMessage extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String businessKey;

  @Enumerated(EnumType.STRING)
  private MessageType type;

  private Long version;

  private String content;

  private Boolean deleted;

}
