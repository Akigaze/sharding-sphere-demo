package demo.sharding.sphere.shardingjdbc.repository;

import demo.sharding.sphere.shardingjdbc.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
