package demo.sharding.sphere.common.model.base;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Comparable<BaseEntity> {

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_time")
  private Date createdTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_time")
  private Date updatedTime;

  @Override
  public int compareTo(BaseEntity other) {
    if (other != null) {
      if (this.getCreatedTime() == null || other.getCreatedTime() == null) {
        return 0;
      }
      return this.getCreatedTime().after(other.getCreatedTime()) ? -1 : 1;
    }
    return -1;
  }

  @PrePersist
  public void prePersist() {
    Date now = new Date();
    if (this.createdTime == null) {
      this.createdTime = now;
    }
    if (updatedTime == null) {
      updatedTime = now;
    }
  }

  @PreUpdate
  public void preUpdate() {
    updatedTime = new Date();
  }
}
