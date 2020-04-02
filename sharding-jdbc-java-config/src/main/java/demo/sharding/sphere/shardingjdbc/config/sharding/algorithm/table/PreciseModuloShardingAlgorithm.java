package demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.table;

import java.util.Collection;

import demo.sharding.sphere.shardingjdbc.config.sharding.util.ShardingUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.util.Assert;

@Slf4j
@NoArgsConstructor
public class PreciseModuloShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

  public static final int SHRINK_FACTOR = 257;

  private int modulo = 12;

  public PreciseModuloShardingAlgorithm(int modulo) {
    Assert.isTrue(modulo < SHRINK_FACTOR && modulo > 0 , "module should less than " + SHRINK_FACTOR + " and greater than 0");
    this.modulo = modulo;
  }

  @Override
  public String doSharding(Collection<String> availableTables, PreciseShardingValue<Long> shardingValue) {
    Long id = shardingValue.getValue();

    long order = id % SHRINK_FACTOR % modulo;

    String expectedTables = ShardingUtil.makeTableName(shardingValue.getLogicTableName(), order);

    return availableTables.contains(expectedTables) ? expectedTables : shardingValue.getLogicTableName();
  }
}
