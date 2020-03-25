package shardingjdbc4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import shardingjdbc4.constant.Category;
import shardingjdbc4.model.entity.Event;
import shardingjdbc4.model.param.EventParam;
import shardingjdbc4.repository.EventRepository;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  public Page<Event> getAll(int pageNum, int pageSize) {
    return eventRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public Page<Event> getByCategory(Category category, int pageNum, int pageSize) {
    return eventRepository
        .findAllByCategory(category, PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public Long getMaxIdOfCategory(Category category) {
    return eventRepository.findMaxIdOfCategory(category);
  }

  public Event add(EventParam param){
    Event event = param.convertTo();
    eventRepository.save(event);
    return event;
  }

  public Event getById(Long id) {
    return eventRepository.findById(id).orElse(null);
  }
}
