package demo.sharding.sphere.shardingjdbc.config.sharding.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shardingsphere.api.sharding.ShardingValue;
import org.apache.shardingsphere.core.rule.TableRule;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

public interface TableWrapper<I> extends ShardingWrapper {

  TableRule getTableRule();

  Map<String, I> getActualTableMap();

  I getUpperTable();

  I getLowerTable();

  default <S extends ShardingValue> S getShardingValue() {
    return null;
  }
}
