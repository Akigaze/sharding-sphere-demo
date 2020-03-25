package shardingjdbc4.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shardingjdbc4.annotation.Sharding;
import shardingjdbc4.constant.Species;
import shardingjdbc4.model.entity.Animal;

@Sharding
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
  List<Animal> findAllBySpecies(Species species);

  Page<Animal> findAllBySpecies(Species species, Pageable pageable);

  @Query(value = "select * from animals where species = ?1", nativeQuery = true)
  List<Animal> findAllOfSpecies(int species);

  @Query(value = "select max(id) from animals where species = ?1", nativeQuery = true)
  Long findMaxIdOfSpecies(int species);

  @Query(value = "select count(*) from (select * from animals where species = ?1)", nativeQuery = true)
  Long complexCountBySpecies(int species);
}
