package shardingjdbc4.model.entity;

import demo.sharding.sphere.common.model.base.BaseEntity;
import java.util.Date;
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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import shardingjdbc4.constant.Species;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@DynamicInsert
@Table(name = "animals")
@NoArgsConstructor
public class Animal extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.ORDINAL)
  private Species species;

  private Boolean extinct;

  @Temporal(TemporalType.DATE)
  private Date recordDate;

  public Animal(String name, Species species, Boolean extinct, Date recordDate) {
    this.name = name;
    this.species = species;
    this.extinct = extinct;
    this.recordDate = recordDate;
  }

  @PrePersist
  public void prePersist(){
    if (extinct == null){
      extinct = false;
    }
  }
}
