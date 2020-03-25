package shardingjdbc.config.strategy.algorithm;

import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
public final class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

  private Integer modulo = 4;

  public PreciseModuloShardingTableAlgorithm(Integer modulo) {
    Assert.state(modulo != null && modulo > 0, "module should be not null and greater than 0!");
    this.modulo = modulo;
  }

  @Override
  public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
    String tableSuffix = shardingValue.getValue() % modulo + "";
    for (String table : tableNames) {
      if (table.endsWith(tableSuffix)) {
        return table;
      }
    }
    return IterableUtils.first(tableNames);
//    throw new UnsupportedOperationException("table suffix [" + tableSuffix + "] can not be found!");
  }
}

