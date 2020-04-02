package demo.sharding.sphere.shardingjdbc.config.sharding;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import demo.sharding.sphere.shardingjdbc.ShardingProperties;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.MonthlyShardingTableWrapper;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.MonthlyTable;
import demo.sharding.sphere.shardingjdbc.config.sharding.wrapper.TableWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static demo.sharding.sphere.shardingjdbc.config.sharding.keygenerator.CustomizedKeyGenerator.EPOCH;

@Component
public class ShardingManager {

  public final Pattern TABLE_PATTERN = Pattern.compile("[\\d\\w]*?_((:?\\d{2})_(:?10|11|12|[1-9]))");

  private ShardingConfiguration shardingConfig;

  private ShardingProperties properties;

  private ShardingRule shardingRule;

  private DateTimeFormatter dateTimeFormatter;

  @Autowired
  public ShardingManager(ShardingConfiguration shardingConfig, ShardingProperties properties){
    this.shardingConfig = shardingConfig;
    this.properties = properties;
    this.dateTimeFormatter = DateTimeFormatter.ofPattern(properties.getPattern());
    this.initShardingRule(shardingConfig);
    this.initTableMap(this.shardingRule);
  }

  @Getter
  private static final Map<String, TableWrapper<MonthlyTable>> TABLE_MAP = new HashMap<>();

  public static boolean isBeforeLowerBound(String logicTable, LocalDate localDate){
    Assert.isTrue(TABLE_MAP.containsKey(logicTable), "Invalid table: "+logicTable);
    Assert.isTrue(localDate != null, "localDate should be not null");
    TableWrapper<MonthlyTable> tableWrapper = TABLE_MAP.get(logicTable);
    return tableWrapper.getLowerTable().isAfter(localDate);
  }


  public static boolean isAfterUpperBound(String logicTable, LocalDate localDate){
    Assert.isTrue(TABLE_MAP.containsKey(logicTable), "Invalid table: "+logicTable);
    Assert.isTrue(localDate != null, "localDate should be not null");
    TableWrapper<MonthlyTable> tableWrapper = TABLE_MAP.get(logicTable);
    return tableWrapper.getUpperTable().isBefore(localDate);
  }

  private void initShardingRule(ShardingConfiguration shardingConfig) {
    ShardingDataSource dataSource = (ShardingDataSource) shardingConfig.getShardingDataSource();
    ShardingRuntimeContext runtimeContext = dataSource.getRuntimeContext();
    this.shardingRule = runtimeContext.getRule();
  }

  private void initTableMap(ShardingRule shardingRule) {
    Collection<TableRule> tableRules = shardingRule.getTableRules();

    for (TableRule rule : tableRules) {
      List<DataNode> dataNodes = rule.getActualDataNodes();
      for (DataNode node : dataNodes) {
        String logicTable = rule.getLogicTable();
        String actualTable = node.getTableName();
        MonthlyTable monthlyTable = this.buildMonthlyTable(logicTable, actualTable);
        if (!TABLE_MAP.containsKey(logicTable)) {
          TABLE_MAP.put(logicTable, new MonthlyShardingTableWrapper(rule));
        }
        TABLE_MAP.get(logicTable).getActualTableMap().put(actualTable, monthlyTable);
      }
    }
  }

  private MonthlyTable buildMonthlyTable(String logicTable, String actualTable) {
    if (logicTable.equals(actualTable)){
      LocalDate localDate = LocalDate.from(Instant.ofEpochMilli(EPOCH));
      return new MonthlyTable(actualTable, localDate);
    }
    Matcher matcher = TABLE_PATTERN.matcher(actualTable);
    Assert.isTrue(matcher.find(), "sharding table not match: " + actualTable);
    LocalDate localDate = LocalDate.parse(matcher.group(1), dateTimeFormatter);
    return new MonthlyTable(actualTable, localDate);
  }


}
