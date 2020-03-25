package demo.sharding.jdbc3.repository;


import demo.sharding.jdbc3.model.entity.CsbcMessageProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsbcMessageProcessRepository extends JpaRepository<CsbcMessageProcess, Long> {

}
