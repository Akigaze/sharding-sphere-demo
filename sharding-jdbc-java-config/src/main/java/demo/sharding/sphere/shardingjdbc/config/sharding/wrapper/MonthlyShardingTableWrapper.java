package demo.sharding.sphere.shardingjdbc.config.sharding.wrapper;

import lombok.Data;
import org.apache.shardingsphere.api.sharding.ShardingValue;
import org.apache.shardingsphere.core.rule.TableRule;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class MonthlyShardingTableWrapper implements TableWrapper<MonthlyTable> {
  private TableRule tableRule;

  private Map<String, MonthlyTable> actualTableMap;

  public MonthlyShardingTableWrapper(TableRule tableRule) {
    this.tableRule = tableRule;
    this.actualTableMap = new HashMap<>();
  }

  @Override
  public String getLogicTable() {
    return tableRule.getLogicTable();
  }

  @Override
  public Collection<String> getAvailableTables() {
    return tableRule.getActualDatasourceNames();
  }

  @Override
  public TableRule getTableRule() {
    return tableRule;
  }

  @Override
  public Map<String, MonthlyTable> getActualTableMap() {
    return actualTableMap;
  }

  @Override
  public MonthlyTable getUpperTable() {
    return actualTableMap.values().stream().max(MonthlyTable::compareTo).orElseThrow(() -> new RuntimeException("actual tables not exited"));
  }

  @Override
  public MonthlyTable getLowerTable() {
    return actualTableMap.values().stream().min(MonthlyTable::compareTo).orElseThrow(() -> new RuntimeException("actual tables not exited"));
  }
}
