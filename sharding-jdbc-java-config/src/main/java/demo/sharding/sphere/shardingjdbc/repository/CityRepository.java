package demo.sharding.sphere.shardingjdbc.repository;

import demo.sharding.sphere.shardingjdbc.constant.enums.Scale;
import demo.sharding.sphere.shardingjdbc.model.entity.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
  Optional<City> findFirstByScale(Scale scale);
}
