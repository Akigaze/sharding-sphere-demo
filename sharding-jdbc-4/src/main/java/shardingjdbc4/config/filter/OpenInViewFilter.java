package shardingjdbc4.config.filter;



import static shardingjdbc4.config.filter.OpenInViewFilter.PersistenceUnit.nonSharding;
import static shardingjdbc4.config.filter.OpenInViewFilter.PersistenceUnit.sharding;
import static shardingjdbc4.constant.JpaConstant.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class OpenInViewFilter extends OpenEntityManagerInViewFilter {
  enum PersistenceUnit {
    sharding(SHARDING_ENTITY_MANAGER_FACTORY_NAME, SHARDING_PERSISTENCE_UNIT_NAME),
    nonSharding(NON_SHARDING_ENTITY_MANAGER_FACTORY_NAME, NON_SHARDING_PERSISTENCE_UNIT_NAME);

    PersistenceUnit(String factoryName, String unitName) {
      this.factoryName = factoryName;
      this.unitName = unitName;
    }

    private String factoryName;
    private String unitName;
  }

  private Set<String> shardingPathPatterns = new HashSet<>(0);

  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  private final Map<PersistenceUnit, EntityManagerFactory> entityManagerFactoryMap = new HashMap<>();

  @NonNull
  @Override
  protected EntityManagerFactory lookupEntityManagerFactory(HttpServletRequest request) {
    String requestPath = request.getServletPath();
    boolean isShardingRequest = shardingPathPatterns.stream()
        .anyMatch(pattern -> antPathMatcher.match(pattern, requestPath));
    PersistenceUnit unit = isShardingRequest ? sharding : nonSharding;
    EntityManagerFactory factory = entityManagerFactoryMap.get(unit);
    if (factory == null) {
      factory = lookupEntityManagerFactory(unit);
      this.setEntityManagerFactoryForPersistenceUnit(unit, factory);
    }
    logger.info(String.format("OpenInViewFilter lookup EntityManagerFactory: [%s] for request: [%s]",
        unit.factoryName, requestPath));
    return factory;
  }

  private void setEntityManagerFactoryForPersistenceUnit(PersistenceUnit unit, EntityManagerFactory factory) {
    synchronized (entityManagerFactoryMap) {
      if (entityManagerFactoryMap.get(unit) != null) {
        return;
      }
      entityManagerFactoryMap.put(unit, factory);
    }
  }

  private EntityManagerFactory lookupEntityManagerFactory(PersistenceUnit unit) {
    WebApplicationContext wac = WebApplicationContextUtils
        .getRequiredWebApplicationContext(getServletContext());
    String emfBeanName = unit.factoryName;
    String puName = unit.unitName;
    if (StringUtils.hasLength(emfBeanName)) {
      return wac.getBean(emfBeanName, EntityManagerFactory.class);
    } else if (!StringUtils.hasLength(puName) && wac.containsBean(DEFAULT_ENTITY_MANAGER_FACTORY_BEAN_NAME)) {
      return wac.getBean(DEFAULT_ENTITY_MANAGER_FACTORY_BEAN_NAME, EntityManagerFactory.class);
    } else {
      return EntityManagerFactoryUtils.findEntityManagerFactory(wac, puName);
    }
  }

  public void addShardingPathPatterns(String... patterns) {
    Collections.addAll(shardingPathPatterns, patterns);
  }

}
