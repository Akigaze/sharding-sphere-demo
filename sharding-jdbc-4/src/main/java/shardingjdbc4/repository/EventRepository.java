package shardingjdbc4.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shardingjdbc4.constant.Category;
import shardingjdbc4.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  List<Event> findAllByCategory(Category category);

  Page<Event> findAllByCategory(Category category, Pageable pageable);

  @Query(value = "select * from events where category = ?1", nativeQuery = true)
  List<Event> findAllOfCategory(Category category);

  @Query(value = "select max(id) from events where category = ?1", nativeQuery = true)
  Long findMaxIdOfCategory(Category category);
}
