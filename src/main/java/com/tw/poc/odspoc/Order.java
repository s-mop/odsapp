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

    private Integer year;

    private Long userId;
    private Long storeId;

    private BigDecimal totalPrice;
    private BigDecimal payPrice;

    private String orderType;

    private LocalDateTime orderTime;
    private LocalDateTime syncTime;

    private List<OrderItem> orderItems;
    private List<OrderCoupon> coupons;
}
