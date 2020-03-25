package shardingjdbc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shardingjdbc.model.entity.Good;

public interface GoodRepository extends JpaRepository<Good, Long> {

}
