package demo.sharding.sphere.config.strategy.algorithm;

import demo.sharding.sphere.ShardingStrategyProperties;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatedTimeShardingFormatter {

  public static SimpleDateFormat suffixFormat;

  public static String getTableSuffix(Date date) {
    return getSuffixFormat().format(date);
  }

  public static SimpleDateFormat getSuffixFormat() {
    if (suffixFormat == null) {
      suffixFormat = new SimpleDateFormat(ShardingStrategyProperties.getCreatedTimeShardingRule());
    }
    return suffixFormat;
  }
}
