package shardingjdbc4.config.jpa;


import static shardingjdbc4.constant.JpaConstant.*;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import shardingjdbc4.annotation.Sharding;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "shardingjdbc4.repository",
    excludeFilters = @Filter(type = FilterType.ANNOTATION, value = Sharding.class),
    entityManagerFactoryRef = NON_SHARDING_ENTITY_MANAGER_FACTORY_NAME,
    transactionManagerRef = NON_SHARDING_TRANSACTION_MANAGER_NAME
)
@ConditionalOnProperty(prefix = "shardingjdbc4.sharding", name = "enabled", havingValue = "true", matchIfMissing = false)
public class NonShardingJpaRepositoryConfiguration {

  @Autowired
  private JpaProperties jpaProperties;

  @Autowired
  private DataSource dataSource;

  @Primary
  @Bean(NON_SHARDING_ENTITY_MANAGER_NAME)
  public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
    return Objects.requireNonNull(entityManagerFactory(builder).getObject()).createEntityManager();
  }

  @Primary
  @Bean(NON_SHARDING_ENTITY_MANAGER_FACTORY_NAME)
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(dataSource)
        .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
        .packages("shardingjdbc4.model.entity")
        .persistenceUnit(NON_SHARDING_PERSISTENCE_UNIT_NAME)
        .build();
  }

  @Primary
  @Bean(NON_SHARDING_TRANSACTION_MANAGER_NAME)
  public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
    return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
  }

}
