package demo.sharding.sphere.shardingjdbc.controller;

import demo.sharding.sphere.shardingjdbc.service.GoodService;
import demo.sharding.sphere.shardingjdbc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sharding/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
  private final GoodService goodService;

  private final OrderService orderService;
}
