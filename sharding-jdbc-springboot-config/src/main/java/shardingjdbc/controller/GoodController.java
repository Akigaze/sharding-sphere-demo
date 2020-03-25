package shardingjdbc.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shardingjdbc.model.entity.Good;
import shardingjdbc.model.param.GoodParam;
import shardingjdbc.service.GoodService;

@RestController
@RequestMapping("/api/sharding/good")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GoodController {

  private final GoodService goodService;

  @GetMapping
  public Page<Good> getAll(@RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "20") int pageSize) {
    return goodService.getAll(pageNum, pageSize);
  }

  @PostMapping
  public void add(@RequestBody GoodParam goodParam){
    goodService.save(goodParam);
  }
}
