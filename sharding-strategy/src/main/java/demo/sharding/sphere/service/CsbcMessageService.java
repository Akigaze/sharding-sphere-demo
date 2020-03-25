package demo.sharding.sphere.service;


import demo.sharding.sphere.model.entity.CsbcMessage;
import demo.sharding.sphere.model.param.CsbcMessageParam;
import demo.sharding.sphere.repository.CsbcMessageRepository;
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
public class CsbcMessageService {

  private final CsbcMessageRepository messageRepository;

  public Page<CsbcMessage> getAll(int pageNum, int pageSize) {
    return messageRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public void save(CsbcMessageParam param) {
    CsbcMessage entity = param.convertTo();
    messageRepository.save(entity);
  }

  public void update(Long id, CsbcMessageParam param) {
    Optional<CsbcMessage> optionEntity = messageRepository.findById(id);
    if (optionEntity.isPresent()) {
      CsbcMessage entity = optionEntity.get();
      param.update(entity);
      messageRepository.save(entity);
    }
  }

  public CsbcMessage getById(Long id) {
    return messageRepository.findById(id).orElse(null);
  }
}
