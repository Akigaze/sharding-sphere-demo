package demo.sharding.sphere.shardingjdbc.config.sharding.util;

import org.springframework.util.Assert;

public class ShardingUtil {

  public static final String SEPARATOR = "_";

  public static String makeTableName(String logicName, Object... args) {
    Assert.hasText(logicName, "Logic table name should not be null or empty");

    if (args.length == 0) {
      return logicName;
    }

    StringBuilder builder = new StringBuilder(logicName);
    for (Object arg : args) {
      builder.append(SEPARATOR).append(arg);
    }
    return builder.toString();
  }
}
