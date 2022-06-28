package com.tw.poc.odspoc;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @PostMapping(path = "syncOrder")
    public Order syncOrder(@RequestBody Order order) {
        Order mo = mockOneOrder();
        BeanUtils.copyProperties(order, mo);
        return orderRepository.save(mo);
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

    private Order mockOneOrder() {
        Order o = new Order();

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
