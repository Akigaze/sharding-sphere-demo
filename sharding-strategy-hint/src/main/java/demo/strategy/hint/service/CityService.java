package demo.strategy.hint.service;

import demo.strategy.hint.model.entity.City;
import demo.strategy.hint.model.param.CityParam;
import demo.strategy.hint.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CityService {

  @Autowired
  private CityRepository animalRepository;

  public Page<City> getAll(int pageNum, int pageSize) {
    return animalRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public City add(CityParam param){
    City animal = param.convertTo();
    animalRepository.save(animal);
    return animal;
  }

  public City getById(Long id) {
    return animalRepository.findById(id).orElse(null);
  }

}