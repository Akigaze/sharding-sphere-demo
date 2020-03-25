package demo.sharding.jdbc3.service;



import demo.sharding.jdbc3.model.entity.CsbcMessageProcess;
import demo.sharding.jdbc3.model.param.CsbcMessageProcessParam;
import demo.sharding.jdbc3.repository.CsbcMessageProcessRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CsbcMessageProcessService {

  private final CsbcMessageProcessRepository messageRepository;

  public Page<CsbcMessageProcess> getAll(int pageNum, int pageSize) {
    return messageRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public void save(CsbcMessageProcessParam param) {
    CsbcMessageProcess entity = param.convertTo();
    messageRepository.save(entity);
  }

  public void update(Long id, CsbcMessageProcessParam param) {
    Optional<CsbcMessageProcess> optionEntity = messageRepository.findById(id);
    if (optionEntity.isPresent()) {
      CsbcMessageProcess entity = optionEntity.get();
      param.update(entity);
      messageRepository.save(entity);
    }
  }

  public CsbcMessageProcess getById(Long id) {
    return messageRepository.findById(id).orElse(null);
  }
}
