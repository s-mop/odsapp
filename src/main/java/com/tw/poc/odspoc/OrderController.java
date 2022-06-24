package com.tw.poc.odspoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @PostMapping(path = "syncOrder")
    public Order syncOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
}
