package demo.execute.controller;

import demo.execute.model.entity.City;
import demo.execute.model.param.CityParam;
import demo.execute.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/animal")
public class CityController {

  @Autowired
  private CityService cityService;

  @GetMapping
  public Page<City> getAll(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
    return cityService.getAll(pageNum, pageSize);
  }

  @GetMapping("/{id}")
  public City getById(@PathVariable Long id) {
    return cityService.getById(id);
  }

  @PostMapping
  public City add(@RequestBody CityParam param){
    return cityService.add(param);
  }

  @GetMapping("/count")
  public Long count(){
    return cityService.count();
  }

}
