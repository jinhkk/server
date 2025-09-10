package com.tableorder.server.dto;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class AggregatedOrderItemDto {
    private String menuName;
    private int totalQuantity;
    private BigDecimal totalPrice;

    public AggregatedOrderItemDto(String menuName, int totalQuantity, BigDecimal totalPrice) {
        this.menuName = menuName;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }
}