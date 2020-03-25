package shardingjdbc4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shardingjdbc4.constant.Category;
import shardingjdbc4.model.entity.Event;
import shardingjdbc4.model.param.EventParam;
import shardingjdbc4.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {

  @Autowired
  private EventService eventService;

  @GetMapping
  public Page<Event> getAll(@RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize) {
    return eventService.getAll(pageNum, pageSize);
  }

  @GetMapping("/category/{category}")
  public Page<Event> getAllBySpecies(@PathVariable Category category,
      @RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize) {
    return eventService.getByCategory(category, pageNum, pageSize);
  }

  @GetMapping("/category/max/{category}")
  public Long getMaxIdOfSpecies(@PathVariable Category category) {
    return eventService.getMaxIdOfCategory(category);
  }

  @GetMapping("/{id}")
  public Event getById(@PathVariable Long id) {
    return eventService.getById(id);
  }

  @PostMapping
  public Event add(@RequestBody EventParam param) {
    return eventService.add(param);
  }

}
