package demo.sharding.sphere.model.param;


import demo.sharding.sphere.common.model.base.InputConverter;
import demo.sharding.sphere.model.entity.CsbcMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsbcMessageParam implements InputConverter<CsbcMessage> {

  private String businessKey;

  private String type;

  private String eventId;

  private String assetId;

  private String sender;

  private String receiver;

  private String receiverRoles;

  private Integer version;

  private String content;

  private String exceptionLog;

  private String processState;
}
