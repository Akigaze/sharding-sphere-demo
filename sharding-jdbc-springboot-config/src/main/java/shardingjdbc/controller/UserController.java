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
import shardingjdbc.model.entity.User;
import shardingjdbc.model.param.GoodParam;
import shardingjdbc.model.param.UserParam;
import shardingjdbc.service.UserService;

@RestController
@RequestMapping("/api/sharding/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;

  @GetMapping
  public Page<User> getAll(@RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "20") int pageSize) {
    return userService.getAll(pageNum, pageSize);
  }

  @PostMapping
  public void add(@RequestBody UserParam goodParam){
    userService.save(goodParam);
  }
}
