package demo.execute;

import static demo.execute.constant.DataFactory.COUNTRIES;

import demo.execute.constant.Scale;
import demo.execute.model.entity.City;
import demo.execute.repository.CityRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

@Slf4j
@SpringBootApplication
public class ShardingExecuteEngineApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication
        .run(ShardingExecuteEngineApplication.class, args);

    String[] namesForType = context.getBeanNamesForType(DataSource.class);

    for (String name : namesForType) {
      log.info("data source bean name: {}", name);
    }

    CityRepository cityRepository = context.getBean(CityRepository.class);
    JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
    PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);
    TransactionDefinition transactionDefinition = context.getBean(TransactionDefinition.class);

    log.info("--------------------------------------------------");
//    initData(cityRepository, transactionManager, transactionDefinition);
    for (int i = 0; i < 20; i++) {

      jdbcTemplateTest(jdbcTemplate);

      shardingTest(cityRepository);
    }

  }

  private static void shardingTest(CityRepository cityRepository) {
    List<Long> costs = new ArrayList<>();
    for (String country : COUNTRIES) {
      long cost = getMaxPopulationCityOfCountry(cityRepository, country);
      costs.add(cost);
    }
    removeTheMax(costs);
    log.info("sharding average: {} ", costs.stream().reduce(Long::sum).orElse(0L) / costs.size());
  }

  private static void jdbcTemplateTest(JdbcTemplate jdbcTemplate) {
    List<Long> costs = new ArrayList<>();
    List<Long> populations = new ArrayList<>();
    List<String> tables = Arrays
        .asList("cities_0", "cities_1", "cities_2", "cities_3", "cities_4", "cities_5", "cities_6",
            "cities_7", "cities_8", "cities_9", "cities_10", "cities_11");
    for (String country : COUNTRIES) {
      long start = System.currentTimeMillis();
      for (int i = 0; i < 4; i++) {
        Long population = jdbcTemplate
            .queryForObject(String.format("select max(population) from %s where country = ?", tables.get(i)),
                Long.class, country);
        populations.add(population == null ? -1 : population);
      }
      long cost = System.currentTimeMillis() - start;

      log.debug("jdbcTemplate cost: {} for {}", cost,
          Collections.max(populations.stream().filter(Objects::nonNull).collect(Collectors.toList())));
      costs.add(cost);
    }
    removeTheMax(costs);

    log.info("jdbcTemplate average: {} ", costs.stream().reduce(Long::sum).orElse(0L) / costs.size());
  }

  private static void getFirstCityOfScale(CityRepository cityRepository, Scale scale) {
    long start = System.currentTimeMillis();
    Optional<City> city = cityRepository.findFirstByScale(scale);
    log.debug("cost: {} for {}", (System.currentTimeMillis() - start), city.orElse(null));
  }

  private static long getMaxPopulationCityOfCountry(CityRepository cityRepository, String country) {
    long start = System.currentTimeMillis();
    Optional<Long> population = cityRepository.findMaxPopulationOfCountry(country);
    long cost = System.currentTimeMillis() - start;
    log.debug("sharding cost: {} for {}", cost, population.orElse(-1L));
    return cost;
  }

  private static void initData(CityRepository cityRepository,
      PlatformTransactionManager transactionManager,
      TransactionDefinition transactionDefinition) {
    int batch = 5000;
    int times = 100;
    long start = System.currentTimeMillis();
    for (int n = 0; n < times; n ++){
      TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
      for (int i = 0; i < batch; i++) {
        City city = new City();
        double random = Math.random();
        city.setName(RandomStringUtils.randomAlphanumeric((int) (random * 20 + 8)));
        city.setCountry(COUNTRIES.get((int) (random * COUNTRIES.size())));
        city.setPopulation((long)(random * 100000 + 100));
        city.setExisted((random * 2) > 1);
        city.setScale(Scale.values()[(int) (random * 4)]);
        cityRepository.save(city);
        log.info("create No.{}.{} city: {}", n, i, city.getName());
      }
      transactionManager.commit(transactionStatus);
    }
    log.warn("total cost: {} for {} records", System.currentTimeMillis() - start, times * batch);

  }

  public static void removeTheMax(List<Long> list) {
    list.remove(Collections.max(list));
  }

}
