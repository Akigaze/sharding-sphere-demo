package demo.execute.repository;

import demo.execute.constant.Scale;
import demo.execute.model.entity.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
  Optional<City> findFirstByScale(Scale scale);

  @Query(value = "select max(population) from cities where country = ?1", nativeQuery=true )
  Optional<Long> findMaxPopulationOfCountry(String country);

  @Query(value = "select max(population) from cities_1 where country = ?1", nativeQuery=true )
  Optional<Long> findMaxPopulationOfCountryInTable1(String country);
}
