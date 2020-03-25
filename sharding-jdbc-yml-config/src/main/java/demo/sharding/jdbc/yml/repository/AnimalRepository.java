package demo.sharding.jdbc.yml.repository;

import demo.sharding.jdbc.yml.annotation.Sharding;
import demo.sharding.jdbc.yml.constant.Species;
import demo.sharding.jdbc.yml.model.entity.Animal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Sharding
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
  List<Animal> findAllBySpecies(Species species);

  @Query(value = "select * from animals where species = ?1", nativeQuery = true)
  List<Animal> findAllOfSpecies(int species);

  @Query(value = "select max(id) from animals where species = ?1", nativeQuery = true)
  Long findMaxIdOfSpecies(int species);
}
