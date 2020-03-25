package demo.sharding.sphere.model.param;


import demo.sharding.sphere.common.model.base.InputConverter;
import demo.sharding.sphere.model.entity.CsbcMessageProcess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsbcMessageProcessParam implements InputConverter<CsbcMessageProcess> {

  private String businessKey;

  private String type;

  private Long csbcId;

  private String eventId;

  private String assetId;

  private Integer version;

  private String exceptionLog;

  private String processState;
}
