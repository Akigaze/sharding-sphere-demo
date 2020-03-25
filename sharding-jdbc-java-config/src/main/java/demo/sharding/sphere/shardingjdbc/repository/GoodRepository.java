package demo.sharding.sphere.shardingjdbc.repository;

import demo.sharding.sphere.shardingjdbc.model.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {

}
