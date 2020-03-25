package shardingjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shardingjdbc.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
