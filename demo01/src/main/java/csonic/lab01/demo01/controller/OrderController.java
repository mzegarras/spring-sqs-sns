package csonic.lab01.demo01.controller;

import csonic.lab01.demo01.dto.OrderDto;
import csonic.lab01.demo01.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @GetMapping("/demo01")
    String demo01() {
        this.orderService.send("demo01");
        return "ok";
    }

    @GetMapping("/demo02")
    String demo02() {
        var dto = OrderDto.builder().build();

        this.orderService.sendOrder(dto);

        return "ok";
    }
}
