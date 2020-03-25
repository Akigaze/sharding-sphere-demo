package demo.sharding.sphere.shardingjdbc.config.strategy.algorithm;

import demo.sharding.sphere.shardingjdbc.config.strategy.AlgorithmConstant;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Scope("prototype")
@NoArgsConstructor
@Component(AlgorithmConstant.TIMING)
public final class TimingShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

  @Getter
  @AllArgsConstructor
  public
  enum TimingUnit {
    YEAR(1, ChronoUnit.YEARS),
    MONTH(1, ChronoUnit.MONTHS),
    WEEK(1, ChronoUnit.WEEKS),
    DAY(1, ChronoUnit.YEARS),
    HOUR(24, ChronoUnit.HOURS),
    MINUTE(24 * 60, ChronoUnit.MINUTES),
    SECOND(24 * 60 * 60, ChronoUnit.SECONDS);

    private Integer interval;
    private TemporalUnit unit;
  }

  private static final LocalDateTime INITIAL_TIME = LocalDateTime
      .ofInstant(Instant.parse("2020-03-04T02:28:00Z"), ZoneId.of("UTC"));

  private TimingUnit by = TimingUnit.MONTH;

  private Integer interval = 1;

  public TimingShardingTableAlgorithm(TimingUnit by, Integer interval) {
    if (by != null) {
      this.by = by;
    }
    this.interval = this.by.getInterval();
    if (interval != null && interval > 0) {
      this.interval = interval;
    }
  }

  @Override
  public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> shardingValue) {
    long elapsedTime = this.getElapsedTime();
    for (String table : tableNames) {
      if (table.endsWith(elapsedTime + "")) {
        return table;
      }
    }
    return IterableUtils.first(tableNames);
  }

  private long getElapsedTime() {
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    long elapsedTime = by.getUnit().between(INITIAL_TIME, now);
    return elapsedTime / interval;
  }
}

