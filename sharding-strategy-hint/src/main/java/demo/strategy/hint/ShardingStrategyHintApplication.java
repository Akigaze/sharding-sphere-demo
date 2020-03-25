package demo.strategy.hint;

import demo.strategy.hint.constant.Scale;
import demo.strategy.hint.model.entity.City;
import demo.strategy.hint.repository.CityRepository;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class ShardingStrategyHintApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication
        .run(ShardingStrategyHintApplication.class, args);

    String[] namesForType = context.getBeanNamesForType(DataSource.class);

    for (String name : namesForType) {
      log.info("data source bean name: {}", name);
    }

    CityRepository cityRepository = context.getBean(CityRepository.class);

    HintManager hintManager = HintManager.getInstance();
    hintManager.addTableShardingValue("cities", "");
    hintManager.addTableShardingValue("cities", "1");

    City zhuhai = new City();
    zhuhai.setName("Zhuhai");
    zhuhai.setCountry("China");
    zhuhai.setPopulation(2023700L);
    zhuhai.setExisted(true);
    zhuhai.setScale(Scale.MIDDLE);
    cityRepository.save(zhuhai);

    hintManager.close();

    List<City> cities = cityRepository.findAll();

    for (City city : cities) {
      System.out.println(city);
    }

    Optional<City> middleCityOptional = cityRepository.findFirstByScale(Scale.MIDDLE);

    middleCityOptional.ifPresent(System.out::println);

  }

}
