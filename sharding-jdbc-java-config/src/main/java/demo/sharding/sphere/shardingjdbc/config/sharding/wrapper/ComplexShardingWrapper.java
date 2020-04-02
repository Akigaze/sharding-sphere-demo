package demo.sharding.sphere.shardingjdbc.config.sharding.wrapper;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

@Getter
@AllArgsConstructor
public class ComplexShardingWrapper implements ShardingWrapper {

  private Collection<String> availableTables;

  private ComplexKeysShardingValue<String> shardingValue;

  public String getLogicTable() {
    return shardingValue.getLogicTableName();
  }
}
