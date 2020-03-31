package demo.execute.config.sharding.algorithm;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

@Slf4j
public class PreciseModuloShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

  public static final int SHARDING_FACTOR = 12;

  public static final int SHRINK_FACTOR = 257;

  @Override
  public String doSharding(Collection<String> availableTables, PreciseShardingValue<Long> shardingValue) {
    Long id = shardingValue.getValue();

    long order = id % SHRINK_FACTOR % SHARDING_FACTOR;

    String expectedTables = shardingValue.getLogicTableName() + "_" + order;

    return availableTables.contains(expectedTables) ? expectedTables : shardingValue.getLogicTableName();
  }

}
