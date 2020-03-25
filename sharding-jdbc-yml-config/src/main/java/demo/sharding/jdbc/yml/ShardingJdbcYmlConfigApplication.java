package demo.sharding.jdbc.yml;

import demo.sharding.jdbc.yml.constant.Species;
import demo.sharding.jdbc.yml.model.entity.Animal;
import demo.sharding.jdbc.yml.repository.AnimalRepository;
import java.util.Date;
import java.util.List;
import javax.sound.midi.SoundbankResource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class ShardingJdbcYmlConfigApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication
        .run(ShardingJdbcYmlConfigApplication.class, args);

    String[] namesForType = context.getBeanNamesForType(DataSource.class);

    for (String name : namesForType) {
      log.info("data source bean name: {}", name);
    }

    AnimalRepository animalRepository = context.getBean(AnimalRepository.class);

    Animal butterFly = new Animal();
    butterFly.setName("butter fly");
    butterFly.setSpecies(Species.INSECT);
    butterFly.setRecordDate(new Date());
    animalRepository.save(butterFly);

    List<Animal> fishes = animalRepository.findAllBySpecies(Species.FISH);

    for (Animal fish : fishes) {
      System.out.println(fish);
    }

    // findAll需要查询所有分表，返回多个记录，这中操作在3.1.0版本有bug
    List<Animal> all = animalRepository.findAll();

    for (Animal animal : all) {
      System.out.println(animal);
    }

    Long maxIdOfSpecies = animalRepository.findMaxIdOfSpecies(Species.FISH.ordinal());
    System.out.println(maxIdOfSpecies);

    List<Animal> allOfSpecies = animalRepository.findAllOfSpecies(Species.FISH.ordinal());

    for (Animal allOfSpecy : allOfSpecies) {
      System.out.println(allOfSpecy);
    }

  }

}
