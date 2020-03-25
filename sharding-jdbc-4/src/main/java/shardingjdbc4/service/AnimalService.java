package shardingjdbc4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import shardingjdbc4.constant.Species;
import shardingjdbc4.model.entity.Animal;
import shardingjdbc4.model.param.AnimalParam;
import shardingjdbc4.repository.AnimalRepository;

@Service
public class AnimalService {

  @Autowired
  private AnimalRepository animalRepository;

  public Page<Animal> getAll(int pageNum, int pageSize) {
    return animalRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public Page<Animal> getBySpecies(Species species, int pageNum, int pageSize) {
    return animalRepository.findAllBySpecies(species, PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public Long getMaxIdOfSpecies(Species species) {
    return animalRepository.findMaxIdOfSpecies(species.ordinal());
  }

  public Animal add(AnimalParam param){
    Animal animal = param.convertTo();
    animalRepository.save(animal);
    return animal;
  }

  public Animal getById(Long id) {
    return animalRepository.findById(id).orElse(null);
  }

  public Long complexCountBySpecies(Species species){
    return animalRepository.complexCountBySpecies(species.ordinal());
  }
}
