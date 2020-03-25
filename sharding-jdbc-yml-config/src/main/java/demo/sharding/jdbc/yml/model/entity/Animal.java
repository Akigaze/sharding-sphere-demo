package demo.sharding.jdbc.yml.model.entity;

import demo.sharding.jdbc.yml.constant.Species;
import demo.sharding.sphere.common.model.base.BaseEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@DynamicInsert
@Table(name = "animals")
public class Animal extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "SPECIES")
  private Species species;

  private Boolean extinct;

  @Temporal(TemporalType.DATE)
  private Date recordDate;

  @PrePersist
  public void prePersist(){
    if (extinct == null){
      extinct = false;
    }
  }
}
