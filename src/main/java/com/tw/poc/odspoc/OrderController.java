package com.tw.poc.odspoc;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @PostMapping(path = "syncOrder")
    public Order syncOrder(@RequestBody Order order) {
        fillOrder(order);
        return orderRepository.save(order);
    }

    @PostMapping(path = "writeOrder")
    public Order writeOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PostMapping(path = "page")
    public Page<Order> page(@RequestBody OrderQuery orderQuery, @PageableDefault Pageable pageable) {
        Order order = new Order();
        order.setUserId(orderQuery.getMemberCode());
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().
                withMatcher("isDeleted", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Order> example = Example.of(order,matcher);
        return orderRepository.findAll(example, pageable);
    }

    @PostMapping(path = "page/{uid}")
    public Slice<Order> slice(@PathVariable("uid") Long userId, @PageableDefault Pageable pageable) {
        return orderRepository.findByUserId(userId,pageable);
    }

    @PostMapping(path = "get")
    public Optional<Order> get(@RequestBody Order o) {
        return orderRepository.findById(o.getId());
    }


    private Order fillOrder(Order o) {
        o.setYear(2022 - new Random().nextInt(5));
        o.setUserId(Long.valueOf((long) (10000 + new Random().nextInt(5000))));
        o.setStoreId(Long.valueOf((long) (20000 + new Random().nextInt(2000))));

        o.setTotalPrice(new BigDecimal(10 + Math.random() * 20).setScale(2,BigDecimal.ROUND_HALF_UP));

        o.setPayPrice(o.getTotalPrice());

        o.setOrderType(String.valueOf(3920 + new Random().nextInt(20)));

        o.setSyncTime(LocalDateTime.now());
        return o;
    }
}
