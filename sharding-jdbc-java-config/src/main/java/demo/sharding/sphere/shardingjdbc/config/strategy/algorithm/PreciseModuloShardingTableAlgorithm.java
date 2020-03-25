package demo.sharding.sphere.shardingjdbc.config.strategy.algorithm;

import demo.sharding.sphere.shardingjdbc.config.strategy.AlgorithmConstant;
import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Data
@Scope("prototype")
@NoArgsConstructor
@Component(AlgorithmConstant.PRECISE_MODULO)
public final class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

  private Integer modulo = 2;

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
    throw new UnsupportedOperationException("table suffix [" + tableSuffix + "] can not be found!");
  }
}

