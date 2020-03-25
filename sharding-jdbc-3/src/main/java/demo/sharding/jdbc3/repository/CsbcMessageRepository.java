package demo.sharding.jdbc3.repository;



import demo.sharding.jdbc3.model.entity.CsbcMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsbcMessageRepository extends JpaRepository<CsbcMessage, Long> {

}
