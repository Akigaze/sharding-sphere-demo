package demo.sharding.sphere.config.strategy.algorithm;

import demo.sharding.sphere.constant.ProcessState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

@Data
@Slf4j
public class CsbcMessageComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

  public static final String COLUMN_TYPE = "type";

  public static final String COLUMN_PROCESS_STATE = "process_state";

  @Override
  public Collection<String> doSharding(Collection<String> tableNames,
      ComplexKeysShardingValue<String> shardingValue) {
    List<String> types = this.getColumnShardingValues(shardingValue, COLUMN_TYPE);
    List<String> processStates = this.getColumnShardingValues(shardingValue, COLUMN_PROCESS_STATE);

    String type = types.stream().findFirst().orElse(null);
    String state = processStates.stream().findFirst().orElse(null);

    final String suffix = (type == null || state == null)
        ? this.makeTableName("0", "0")
        : (ProcessState.isErrorState(state) ? this.makeTableName(type, "0") : this.makeTableName(type, "1"));
    List<String> shardingTables = tableNames.stream().filter(name -> name.endsWith(suffix))
        .collect(Collectors.toList());
    log.info("sharding to tables: [{}]", shardingTables);
    return shardingTables;
  }

  private List<String> getColumnShardingValues(ComplexKeysShardingValue<String> shardingValue,
      String column) {
    return new ArrayList<>(shardingValue.getColumnNameAndShardingValuesMap().get(column));
  }

  private String makeTableName(String type, String state) {
    return (type + "_" + state).toLowerCase();
  }
}
