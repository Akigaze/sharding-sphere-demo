package demo.sharding.sphere.shardingjdbc.config.sharding.strategy.algorithm.table;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import demo.sharding.sphere.shardingjdbc.config.sharding.keygenerator.CustomizedKeyGenerator;
import demo.sharding.sphere.shardingjdbc.config.sharding.util.ShardingUtil;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.ColumnWrapper;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.ComplexShardingWrapper;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.ShardingWrapper;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.StringColumnWrapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

@Slf4j
public class CreatedTimeComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

  public static final String COLUMN_ID = "id";
  public static final String COLUMN_CREATED_TIME = "rec_cre_dt_utc";

  private final DateFormat dateFormat;
  private final String datePattern;

  public CreatedTimeComplexShardingAlgorithm(String pattern) {
    datePattern = pattern;
    dateFormat = new SimpleDateFormat(pattern);
  }

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      ComplexKeysShardingValue<String> shardingValue) {
    Map<String, Collection<String>> shardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();


    Collection<String> ids = shardingValuesMap.get(COLUMN_ID);
    Collection<String> createdTimes = shardingValuesMap.get(COLUMN_CREATED_TIME);

    Set<String> tables = new HashSet<>();
    ShardingWrapper shardingWrapper = new ComplexShardingWrapper(availableTargetNames, shardingValue);

    // for save or precise query by id
    if (CollectionUtils.isNotEmpty(ids)) {
      List<ColumnWrapper<String>> columnWrappers = ids.stream().map(id -> new StringColumnWrapper(COLUMN_ID, id)).collect(Collectors.toList());
      tables.addAll(this.getTablesByIds(shardingWrapper, columnWrappers));
    }

    Map<String, Range<String>> rangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
    Range<String> createdTimeRange = rangeValuesMap.get(COLUMN_CREATED_TIME);

    tables.addAll(this.getTablesByCreatedTimeRange(shardingWrapper, createdTimeRange));
    return tables;
  }

  private Collection<String> getTablesByCreatedTimeRange(ShardingWrapper shardingWrapper, Range<String> timeRange) {
    List<String> tables = new ArrayList<>();

    if (timeRange.hasLowerBound()) {
      tables.addAll(this.getTablesByLowerBoundTime(shardingWrapper, timeRange));
    }

    if (timeRange.hasUpperBound()){

    }

    return tables;
  }

  private Collection<String> getTablesByLowerBoundTime(ShardingWrapper shardingWrapper, Range<String> range) {
    List<String> tables = new ArrayList<>();
    if (range.hasLowerBound()) {
      String time = range.lowerEndpoint();
      String actualTable = this.makeActualTable(shardingWrapper, ZonedDateTime.parse(time).toEpochSecond());
      if (shardingWrapper.getAvailableTables().contains(actualTable)) {

      }
      new ArrayList<>()
      return tables;
    }
    return tables;
  }


  private Collection<String> getTablesByIds(ShardingWrapper shardingWrapper, List<ColumnWrapper<String>> columnWrappers) {
    List<String> tables = new ArrayList<>();
    for (ColumnWrapper<String> wrapper : columnWrappers) {
      tables.add(getTablesById(shardingWrapper, wrapper));
    }
    return tables;
  }

  private String getTablesById(ShardingWrapper shardingWrapper, ColumnWrapper<String> columnWrapper) {
    long id = Long.parseLong(columnWrapper.getValue());
    long millis = CustomizedKeyGenerator.keyToMillis(id);
    String tableName = this.makeActualTable(shardingWrapper, millis);
    log.debug("table: [{}], {}: [{}], milliseconds: [{}]", tableName, columnWrapper.getColumnName(), id, millis);
    return tableName;
  }

  private String makeActualTable(ShardingWrapper wrapper, long millis) {
    String suffix = dateFormat.format(Date.from(Instant.ofEpochMilli(millis)));
    return ShardingUtil.makeTableName(wrapper.getLogicTable(), suffix);
  }

}
