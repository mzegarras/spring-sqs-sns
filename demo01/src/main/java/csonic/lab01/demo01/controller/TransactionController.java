package csonic.lab01.demo01.controller;

import csonic.lab01.demo01.service.OrderService;
import csonic.lab01.demo01.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @GetMapping("/demo01")
    String demo01() {
        this.transactionService.send("demo01");
        return "ok";
    }
}
