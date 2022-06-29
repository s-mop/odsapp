package com.tw.poc.odspoc;

import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private String itemCode = RandomStringUtils.randomAlphanumeric(6);
    private String itemName;
    private BigDecimal itemPrice;
    private BigDecimal itemDiscount;
}
