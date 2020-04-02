package demo.sharding.sphere.shardingjdbc.config.sharding.wrapper;

import java.util.Collection;
import lombok.AllArgsConstructor;
import org.apache.shardingsphere.api.sharding.ShardingValue;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

public interface ShardingWrapper {

  String getLogicTable();

  <S extends ShardingValue> S getShardingValue();

  Collection<String> getAvailableTables();




}
