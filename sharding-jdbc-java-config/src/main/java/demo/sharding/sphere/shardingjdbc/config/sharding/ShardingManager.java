package demo.sharding.sphere.shardingjdbc.config.sharding;

import static csdp.mapper.constant.JpaConstant.SHARDING_ENTITY_MANAGER_FACTORY_NAME;

import csdp.mapper.ShardingConfiguration;
import csdp.mapper.config.sharding.keygenerator.SnowflakeShardingKeyGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "csdp.sharding", name = "enabled", havingValue = "true")
@ConditionalOnBean(name = SHARDING_ENTITY_MANAGER_FACTORY_NAME)
public class ShardingManager {

  @Data
  @AllArgsConstructor
  public static class TableEntry {

    private String tableName;

    private Calendar month;
  }

  public final Pattern TABLE_PATTERN = Pattern.compile("[\\d\\w]*?_((:?\\d{2})_(:?10|11|12|[1-9]))");

  @Autowired
  @Qualifier(SHARDING_ENTITY_MANAGER_FACTORY_NAME)
  private LocalContainerEntityManagerFactoryBean factoryBean;

  @Autowired
  private ShardingConfiguration shardingConfig;

  private ShardingRule shardingRule;

  private Map<String, List<TableEntry>> tableMap = new HashMap<>();


  public List<TableEntry> getActualTablesOf(String logicTable) {
    if (!tableMap.containsKey(logicTable)) {
      TableRule tableRule = this.shardingRule.getTableRule(logicTable);
      Collection<String> tableNames = tableRule.getActualTableNames(shardingConfig.getDatabaseNodeName());
      List<TableEntry> tables = tableNames.stream().map(this::buildTableEntry).collect(Collectors.toList());
      tableMap.put(logicTable, tables);
    }
    return tableMap.get(logicTable);
  }

  private TableEntry buildTableEntry(String tableName) {
    Matcher matcher = TABLE_PATTERN.matcher(tableName);
    SimpleDateFormat format = new SimpleDateFormat(shardingConfig.getCreatedTimeShardingPattern());
    Calendar month = Calendar.getInstance();
    month.setTimeInMillis(SnowflakeShardingKeyGenerator.EPOCH);
    if (matcher.find()) {
      try {
        month.setTime(format.parse(matcher.group(1)));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return new TableEntry(tableName, month);
  }

  @Autowired
  public void setShardingRule(
    @Qualifier(SHARDING_ENTITY_MANAGER_FACTORY_NAME) LocalContainerEntityManagerFactoryBean factoryBean) {
    ShardingDataSource dataSource = (ShardingDataSource) factoryBean.getDataSource();
    ShardingRuntimeContext runtimeContext = dataSource.getRuntimeContext();
    this.shardingRule = runtimeContext.getRule();
  }


}
