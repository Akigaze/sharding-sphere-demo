package shardingjdbc.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import shardingjdbc.model.entity.Good;
import shardingjdbc.model.param.GoodParam;
import shardingjdbc.repository.GoodRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GoodService {

  private final GoodRepository goodRepository;

  public Page<Good> getAll(int pageNum, int pageSize) {
    return goodRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public void save(GoodParam goodParam) {
    Good good = goodParam.convertTo();
    goodRepository.save(good);
  }
}
