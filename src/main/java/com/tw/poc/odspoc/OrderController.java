package com.tw.poc.odspoc;

import org.apache.commons.lang.RandomStringUtils;
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
import java.util.ArrayList;
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

    @PostMapping(path = "slice/{uid}")
    public Slice<Order> slice(@PathVariable("uid") Long userId, @PageableDefault Pageable pageable) {
        return orderRepository.findByUserId(userId,pageable);
    }
    @PostMapping(path = "slice/store/{sid}")
    public Slice<Order> sliceByStore(@PathVariable("sid") Long storeId, @PageableDefault Pageable pageable) {
        return orderRepository.findByStoreId(storeId, pageable);
    }

    @PostMapping(path = "get")
    public Optional<Order> get(@RequestBody Order o) {
        return orderRepository.findById(o.getId());
    }


    private Order fillOrder(Order o) {
        o.setYear(2022 - new Random().nextInt(5));
        o.setUserId(Long.valueOf((long) (10000 + new Random().nextInt(5000))));
        o.setStoreId(Long.valueOf((long) (20000 + new Random().nextInt(2000))));

        o.setTotalPrice(new BigDecimal(30 + Math.random() * 60).setScale(2,BigDecimal.ROUND_HALF_UP));

        o.setPayPrice(o.getTotalPrice());

        o.setOrderType(String.valueOf(3920 + new Random().nextInt(20)));

        o.setSyncTime(LocalDateTime.now());
        ArrayList<OrderCoupon> coupons = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(2) ;i++)
            coupons.add(new OrderCoupon());
        o.setCoupons(coupons);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(3) + 1;i++)
            orderItems.add(mockOrderItem());
        o.setOrderItems(orderItems);
        return o;
    }

    private OrderItem mockOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemCode(RandomStringUtils.randomAlphanumeric(6));
        orderItem.setItemPrice(new BigDecimal(10 + Math.random() * 20).setScale(2,BigDecimal.ROUND_HALF_UP));
        orderItem.setItemDiscount(new BigDecimal(Math.random() * 4).setScale(2,BigDecimal.ROUND_HALF_UP));
        orderItem.setItemName("拿铁-随机名字-" + RandomStringUtils.randomAlphanumeric(1));
        return orderItem;
    }
}
