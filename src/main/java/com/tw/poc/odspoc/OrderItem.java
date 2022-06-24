package com.tw.poc.odspoc;

import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;

@Data
public class OrderItem {
    private String itemCode = RandomStringUtils.randomAlphanumeric(6);
    private String itemName;
    private String itemPrice;
}
