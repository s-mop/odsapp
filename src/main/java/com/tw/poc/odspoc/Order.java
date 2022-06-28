package com.tw.poc.odspoc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Document
@Data
public class Order {
    @Id
    private String id;

    private String orderNO;

    //test sharding strategy
    @Indexed
    private int year = 2022 - new Random().nextInt(5);

    @Indexed
    private Long userId = Long.valueOf((long) (10000 + new Random().nextInt(5000)));
    private Long storeId = Long.valueOf((long) (20000 + new Random().nextInt(2000)));

    private BigDecimal totalPrice = new BigDecimal(10 + Math.random() * 20).setScale(2,BigDecimal.ROUND_HALF_UP);
    private BigDecimal payPrice = totalPrice;

    private LocalDateTime orderTime;
    private LocalDateTime syncTime = LocalDateTime.now();

    private List<OrderItem> orderItems;
    private List<OrderCoupon> coupons;
}
