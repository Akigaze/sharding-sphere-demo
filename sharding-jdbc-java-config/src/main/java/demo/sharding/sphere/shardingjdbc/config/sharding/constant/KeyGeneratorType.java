package demo.sharding.sphere.shardingjdbc.config.sharding.constant;

import demo.sharding.sphere.shardingjdbc.config.sharding.keygenerator.CustomizedKeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.core.strategy.keygen.UUIDShardingKeyGenerator;

@Getter
@AllArgsConstructor
public enum KeyGeneratorType {
  SNOWFLAKE(SnowflakeShardingKeyGenerator.class),

  UUID(UUIDShardingKeyGenerator.class),

  CUSTOMIZED(CustomizedKeyGenerator.class);

  private Class<?> cls;
}
