package demo.sharding.sphere.config.strategy.algorithm;

import demo.sharding.sphere.common.model.base.BaseEntity;
import java.util.Collection;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

@Slf4j
// 使用hint分表策略需要用到 HintManager，在需要进行分表时，手动添加分表字段，可参考 #{HintShardingRouteAop}
public class HintTableShardingAlgorithm implements HintShardingAlgorithm<BaseEntity> {

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      HintShardingValue<BaseEntity> shardingValue) {
    log.info("shardingValue: [{}]", shardingValue);
    BaseEntity first = IterableUtils.first(shardingValue.getValues());
//    String actualTable = shardingValue.getLogicTableName() + "_" + CreatedTimeShardingFormatter.getTableSuffix(first.getCreatedTime());
    String actualTable = shardingValue.getLogicTableName() + "_" + first.getCreatedTime().getTime() % 4;

    return Collections.singletonList(
        availableTargetNames.contains(actualTable) ? actualTable : IterableUtils.first(availableTargetNames));
  }
}
