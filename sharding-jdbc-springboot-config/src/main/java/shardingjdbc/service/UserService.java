package shardingjdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import shardingjdbc.model.entity.User;
import shardingjdbc.model.param.UserParam;
import shardingjdbc.repository.UserRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

  private final UserRepository userRepository;

  public Page<User> getAll(int pageNum, int pageSize) {
    return userRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "id")));
  }

  public void save(UserParam userParam) {
    User user = userParam.convertTo();
    userRepository.save(user);
  }
}
