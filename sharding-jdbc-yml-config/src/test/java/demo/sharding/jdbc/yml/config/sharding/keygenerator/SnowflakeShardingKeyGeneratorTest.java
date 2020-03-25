package demo.sharding.jdbc.yml.config.sharding.keygenerator;

import static demo.sharding.jdbc.yml.config.sharding.keygenerator.SnowflakeShardingKeyGenerator.EPOCH;
import static demo.sharding.jdbc.yml.config.sharding.keygenerator.SnowflakeShardingKeyGenerator.TIMESTAMP_LEFT_SHIFT_BITS;
import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;
import org.junit.Test;

public class SnowflakeShardingKeyGeneratorTest {

  @Test
  public void should_key(){
    SnowflakeShardingKeyGenerator keyGenerator = new SnowflakeShardingKeyGenerator();
    long epochMilli = Instant.now().toEpochMilli();
    System.out.println(epochMilli);
    Long key = keyGenerator.generateKey();
    System.out.println(new Date().getTime());
    System.out.println(System.currentTimeMillis());
    System.out.println(key);
    System.out.println(SnowflakeShardingKeyGenerator.EPOCH);

    Date date = new Date(epochMilli);
    System.out.println(date);
    System.out.println(date.getTime());

    long epochMilli1 = Instant.parse("2020-03-17T01:20:22Z").toEpochMilli();
    System.out.println(epochMilli1);
//    1584407362695
//    1584407362707
    System.out.println((446482076045148263L >> TIMESTAMP_LEFT_SHIFT_BITS) + EPOCH);
  }
}