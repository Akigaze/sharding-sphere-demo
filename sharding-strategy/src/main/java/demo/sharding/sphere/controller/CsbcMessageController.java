package demo.sharding.sphere.controller;


import demo.sharding.sphere.model.entity.CsbcMessage;
import demo.sharding.sphere.model.param.CsbcMessageParam;
import demo.sharding.sphere.service.CsbcMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sharding/msgs")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CsbcMessageController {

  private final CsbcMessageService messageService;

  @GetMapping
  public Page<CsbcMessage> getAll(@RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "20") int pageSize) {
    return messageService.getAll(pageNum, pageSize);
  }

  @GetMapping("/{id}")
  public CsbcMessage getOne(@PathVariable Long id) {
    return messageService.getById(id);
  }

  @PostMapping
  public void add(@RequestBody CsbcMessageParam param) {
    messageService.save(param);
  }

  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody CsbcMessageParam param) {
    messageService.update(id, param);
  }
}
