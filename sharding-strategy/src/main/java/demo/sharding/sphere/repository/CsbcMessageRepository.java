package demo.sharding.sphere.repository;


import demo.sharding.sphere.model.entity.CsbcMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsbcMessageRepository extends JpaRepository<CsbcMessage, Long> {

}
