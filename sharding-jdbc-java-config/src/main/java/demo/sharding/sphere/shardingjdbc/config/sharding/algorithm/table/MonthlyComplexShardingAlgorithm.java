package demo.sharding.sphere.shardingjdbc.config.sharding.algorithm.table;

import com.google.common.collect.Range;
import demo.sharding.sphere.shardingjdbc.config.sharding.ShardingManager;
import demo.sharding.sphere.shardingjdbc.config.sharding.keygenerator.CustomizedKeyGenerator;
import demo.sharding.sphere.shardingjdbc.config.sharding.util.ShardingUtil;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.ColumnWrapper;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.ComplexShardingWrapper;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.ShardingWrapper;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.StringColumnWrapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

@Slf4j
public class MonthlyComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

  public static final String COLUMN_ID = "id";
  public static final String COLUMN_CREATED_TIME = "created_time";
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  private final DateTimeFormatter dateFormat;

  public MonthlyComplexShardingAlgorithm(String pattern) {
    dateFormat = DateTimeFormatter.ofPattern(pattern);
  }

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      ComplexKeysShardingValue<String> shardingValue) {
    Map<String, Collection<String>> shardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();

    ShardingWrapper shardingWrapper = new ComplexShardingWrapper(availableTargetNames, shardingValue);

    Collection<String> ids = shardingValuesMap.get(COLUMN_ID);
    // for save or precise query by id
    Collection<String> tablesByIds = this.getTablesByIds(shardingWrapper, ids);

    Map<String, Range<String>> rangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
    Range<String> createdTimeRange = rangeValuesMap.get(COLUMN_CREATED_TIME);
    Collection<String> tablesByCreatedTimeRange = this.getTablesByCreatedTimeRange(shardingWrapper, createdTimeRange);

    Set<String> tables = new HashSet<>();
    tables.addAll(tablesByIds);
    tables.addAll(tablesByCreatedTimeRange);
    return tables;
  }

  public static String[] getShardingColumns(){
    return new String[]{COLUMN_ID, COLUMN_CREATED_TIME};
  }

  private Collection<String> getTablesByCreatedTimeRange(ShardingWrapper shardingWrapper, Range<String> timeRange) {
    Collection<String> lowerBoundTables = this.getTablesByLowerBoundTime(shardingWrapper, timeRange);
    Collection<String> upperBoundTables = this.getTablesByUpperBoundTime(shardingWrapper, timeRange);
    return CollectionUtils.retainAll(lowerBoundTables, upperBoundTables);
  }

  private Collection<String> getTablesByLowerBoundTime(ShardingWrapper shardingWrapper, Range<String> range) {
    if (range.hasLowerBound()) {
      String time = range.lowerEndpoint();
      ZonedDateTime zonedDateTime = ZonedDateTime.parse(time);
      String actualTable = this.makeActualTable(shardingWrapper, zonedDateTime.toEpochSecond());
      if (shardingWrapper.getAvailableTables().contains(actualTable)) {
        ArrayList<String> tableArrayList = new ArrayList<>(shardingWrapper.getAvailableTables());
        return tableArrayList.subList(tableArrayList.indexOf(actualTable), tableArrayList.size());
      }else if (ShardingManager.isBeforeLowerBound(shardingWrapper.getLogicTable(), zonedDateTime.toLocalDate())){
        return shardingWrapper.getAvailableTables();
      }
    }
    return new ArrayList<>();
  }

  private Collection<String> getTablesByUpperBoundTime(ShardingWrapper shardingWrapper, Range<String> range) {
    if (range.hasUpperBound()) {
      String time = range.upperEndpoint();
      LocalDate localDate = LocalDate.parse(time, DATE_TIME_FORMATTER);
      String actualTable = this.makeActualTable(shardingWrapper, localDate);
      if (shardingWrapper.getAvailableTables().contains(actualTable)) {
        ArrayList<String> tableArrayList = new ArrayList<>(shardingWrapper.getAvailableTables());
        return tableArrayList.subList(0, tableArrayList.indexOf(actualTable) + 1);
      }else if (ShardingManager.isAfterUpperBound(shardingWrapper.getLogicTable(), localDate)){
        return shardingWrapper.getAvailableTables();
      }
    }
    return new ArrayList<>();
  }

  private Collection<String> getTablesByIds(ShardingWrapper shardingWrapper, Collection<String> ids) {
    List<ColumnWrapper<String>> columnWrappers = ids.stream().map(id -> new StringColumnWrapper(COLUMN_ID, id)).collect(Collectors.toList());
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
    String suffix = dateFormat.format(Instant.ofEpochMilli(millis));
    return ShardingUtil.makeTableName(wrapper.getLogicTable(), suffix);
  }

  private String makeActualTable(ShardingWrapper wrapper, LocalDate localDate) {
    String suffix = dateFormat.format(localDate);
    return ShardingUtil.makeTableName(wrapper.getLogicTable(), suffix);
  }

}
