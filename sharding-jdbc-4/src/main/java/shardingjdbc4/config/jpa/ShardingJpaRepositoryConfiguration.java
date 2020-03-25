package shardingjdbc4.config.jpa;

import static shardingjdbc4.constant.JpaConstant.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import shardingjdbc4.ShardingConfiguration;
import shardingjdbc4.annotation.Sharding;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = "shardingjdbc4.repository",
  includeFilters = @Filter(type = FilterType.ANNOTATION, value = Sharding.class),
  entityManagerFactoryRef = SHARDING_ENTITY_MANAGER_FACTORY_NAME,
  transactionManagerRef = SHARDING_TRANSACTION_MANAGER_NAME
)
@ConditionalOnProperty(prefix = "shardingjdbc4.sharding", name = "enabled", havingValue = "true", matchIfMissing = false)
public class ShardingJpaRepositoryConfiguration {

  @Autowired
  private ShardingConfiguration shardingConfig;

  @Autowired
  private JpaProperties jpaProperties;

  @Autowired
  private DataSource dataSource;

  @Bean(SHARDING_ENTITY_MANAGER_NAME)
  public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
    return Objects.requireNonNull(entityManagerFactory(builder).getObject()).createEntityManager();
  }

  @Bean(SHARDING_ENTITY_MANAGER_FACTORY_NAME)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
    DataSource shardingDatasource = createShardingDataSource(dataSource);
    return builder.dataSource(shardingDatasource)
      .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
      .packages("shardingjdbc4.model.entity")
      .persistenceUnit(SHARDING_PERSISTENCE_UNIT_NAME)
      .build();
  }

  @Bean(SHARDING_TRANSACTION_MANAGER_NAME)
  public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
    return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
  }

  public DataSource createShardingDataSource(final DataSource existed) {
    DataSource dataSource = existed;
    log.info("Start to create ShardingDataSource, need to take a while ...");
    log.info(
        "If you don't want to use ShardingDataSource, you can set 'shardingjdbc4.sharding.enabled' to 'false' in springboot config ...");
    long start = System.currentTimeMillis();
    if (shardingConfig.isEnabled()) {
      Map<String, DataSource> dataSourceMap = new HashMap<>();
      dataSourceMap.put(shardingConfig.getDatabaseNodeName(), existed);
      try {
        dataSource = YamlShardingDataSourceFactory.createDataSource(dataSourceMap, new ClassPathResource(shardingConfig.getYamlFilePath()).getFile());
      } catch (Exception e) {
        log.error(
          "Exception occur when createShardingDataSource, database node name: [{}], yaml file path: [{}]",
          shardingConfig.getDatabaseNodeName(), shardingConfig.getYamlFilePath());
        e.printStackTrace();
      }
    }
    log.warn("End creating ShardingDataSource, cost: {} ms", System.currentTimeMillis() - start);
    return dataSource;
  }
}
