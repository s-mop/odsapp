package com.tw.poc.odspoc;

import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;

@Data
public class OrderCoupon {
    private String code = RandomStringUtils.randomAlphanumeric(8);
}
