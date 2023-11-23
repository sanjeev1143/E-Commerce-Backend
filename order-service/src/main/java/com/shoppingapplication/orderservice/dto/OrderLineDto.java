package com.shoppingapplication.orderservice.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Data
public class OrderLineDto {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

}
