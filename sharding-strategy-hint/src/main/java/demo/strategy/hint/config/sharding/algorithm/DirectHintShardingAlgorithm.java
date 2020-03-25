package demo.strategy.hint.config.sharding.algorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

public class DirectHintShardingAlgorithm implements
    HintShardingAlgorithm<String> {

  @Override
  public Collection<String> doSharding(Collection<String> availableTables, HintShardingValue<String> shardingValue) {
    Collection<String> values = shardingValue.getValues();

    Set<String> tables = new HashSet<>();
    for (String order : values) {
      String expectedTables = shardingValue.getLogicTableName() + "_" + order;
      tables.add(availableTables.contains(expectedTables) ? expectedTables : IterableUtils.first(availableTables));
    }

    return tables;
  }
}
