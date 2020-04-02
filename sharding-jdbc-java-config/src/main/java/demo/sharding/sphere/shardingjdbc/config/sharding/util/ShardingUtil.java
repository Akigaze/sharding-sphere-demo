package demo.sharding.sphere.shardingjdbc.config.sharding.util;

import demo.sharding.sphere.shardingjdbc.config.sharding.constant.ShardingConstant;
import org.springframework.util.Assert;

public class ShardingUtil {

  public static String makeTableName(String logicName, Object... args) {
    Assert.hasText(logicName, "Logic table name should not be null or empty");
    StringBuilder builder = new StringBuilder(logicName);
    for (Object arg : args) {
      builder.append(ShardingConstant.SHARDING_TABLE_SEPARATOR).append(arg);
    }
    return builder.toString();
  }

  public static String makeShardingColumns(String ...columns){
    return String.join(ShardingConstant.SHARDING_COLUMNS_SEPARATOR, columns);
  }
}
