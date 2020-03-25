package demo.sharding.jdbc3.model.param;



import demo.sharding.jdbc3.constant.MessageType;
import demo.sharding.jdbc3.model.entity.CsbcMessage;
import demo.sharding.sphere.common.model.base.InputConverter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsbcMessageParam implements InputConverter<CsbcMessage> {

  private String businessKey;

  private MessageType type;

  private Long version;

  private String content;

  private Boolean deleted;
}
