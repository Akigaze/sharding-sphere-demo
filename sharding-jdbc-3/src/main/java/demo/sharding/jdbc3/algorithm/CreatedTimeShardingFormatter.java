package demo.sharding.jdbc3.algorithm;

import demo.sharding.jdbc3.ShardingJdbc3Properties;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatedTimeShardingFormatter {

  public static SimpleDateFormat suffixFormat;

  public static String getTableSuffix(Date date) {
    return getSuffixFormat().format(date);
  }

  public static SimpleDateFormat getSuffixFormat() {
    if (suffixFormat == null) {
      suffixFormat = new SimpleDateFormat(ShardingJdbc3Properties.getCreatedTimeShardingRule());
    }
    return suffixFormat;
  }
}
