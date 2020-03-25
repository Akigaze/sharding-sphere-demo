package shardingjdbc4;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import shardingjdbc4.constant.Species;
import shardingjdbc4.model.entity.Animal;
import shardingjdbc4.repository.AnimalRepository;

@Slf4j
@SpringBootApplication
public class ShardingJdbc4Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication
        .run(ShardingJdbc4Application.class, args);

    String[] namesForType = context.getBeanNamesForType(DataSource.class);

    for (String name : namesForType) {
      log.info("data source bean name: {}", name);
    }

    AnimalRepository animalRepository = context.getBean(AnimalRepository.class);

    Animal animal = new Animal();
    animal.setName("dfdfdggf");
    animal.setSpecies(Species.INSECT);
    animal.setRecordDate(new Date());
    animalRepository.save(animal);

    List<Animal> fishes = animalRepository.findAllBySpecies(Species.FISH);

    for (Animal fish : fishes) {
      System.out.println(fish);
    }

    List<Animal> all = animalRepository.findAll();

    for (Animal an : all) {
      System.out.println(an);
    }

    // jpa native query
    Long maxIdOfFish = animalRepository.findMaxIdOfSpecies(Species.FISH.ordinal());
    System.out.println(maxIdOfFish);

    List<Animal> allFishes = animalRepository.findAllOfSpecies(Species.FISH.ordinal());

    for (Animal fish : allFishes) {
      System.out.println(fish);
    }

  }

}
