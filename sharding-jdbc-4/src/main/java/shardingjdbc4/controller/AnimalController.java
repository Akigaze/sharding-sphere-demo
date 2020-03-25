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
import shardingjdbc4.constant.Species;
import shardingjdbc4.model.entity.Animal;
import shardingjdbc4.model.param.AnimalParam;
import shardingjdbc4.service.AnimalService;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

  @Autowired
  private AnimalService animalService;

  @GetMapping
  public Page<Animal> getAll(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
    return animalService.getAll(pageNum, pageSize);
  }

  @GetMapping("/species/{species}")
  public Page<Animal> getAllBySpecies(@PathVariable Species species, @RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize) {
    return animalService.getBySpecies(species, pageNum, pageSize);
  }

  @GetMapping("/species/max/{species}")
  public Long getMaxIdOfSpecies(@PathVariable Species species) {
    return animalService.getMaxIdOfSpecies(species);
  }

  @GetMapping("/{id}")
  public Animal getById(@PathVariable Long id) {
    return animalService.getById(id);
  }

  @PostMapping
  public Animal add(@RequestBody AnimalParam param){
    return animalService.add(param);
  }

  @GetMapping("/count/{species}") // IllegalStateException: Must have sharding column with subquery.
  public Long complexCount(@PathVariable Species species){
    return animalService.complexCountBySpecies(species);
  }

}
