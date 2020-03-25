package demo.strategy.hint.repository;

import demo.strategy.hint.constant.Scale;
import demo.strategy.hint.model.entity.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CityRepository extends JpaRepository<City, Long> {
  Optional<City> findFirstByScale(Scale scale);
}
