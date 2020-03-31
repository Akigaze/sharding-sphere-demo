package demo.execute.service;

import demo.execute.model.entity.City;
import demo.execute.model.param.CityParam;
import demo.execute.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CityService {

  @Autowired
  private CityRepository cityRepository;

  public Page<City> getAll(int pageNum, int pageSize) {
    return cityRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public City add(CityParam param){
    City animal = param.convertTo();
    cityRepository.save(animal);
    return animal;
  }

  public City getById(Long id) {
    return cityRepository.findById(id).orElse(null);
  }

  public Long count() {
    return cityRepository.count();
  }
}
