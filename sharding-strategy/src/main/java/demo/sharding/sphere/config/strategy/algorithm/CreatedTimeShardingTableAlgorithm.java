package demo.sharding.sphere.config.strategy.algorithm;


import java.util.Collection;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

@Data
@NoArgsConstructor
public final class CreatedTimeShardingTableAlgorithm implements PreciseShardingAlgorithm<Date> {

  @Override
  public String doSharding(Collection<String> tableNames, PreciseShardingValue<Date> shardingValue) {
    String suffix = CreatedTimeShardingFormatter.getTableSuffix(shardingValue.getValue());
    String actualTable = shardingValue.getLogicTableName() + "_" + suffix;
    return tableNames.contains(actualTable) ? actualTable : IterableUtils.first(tableNames);
  }

}

