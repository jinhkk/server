package com.tableorder.server.dto;

import com.tableorder.server.entity.OrderItems;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class UnpaidOrderItemResponseDto {
    private String menuName;
    private int quantity;
    private BigDecimal pricePerItem;

    public UnpaidOrderItemResponseDto(OrderItems orderItem) {
        this.menuName = orderItem.getMenuItem().getName();
        this.quantity = orderItem.getQuantity();
        this.pricePerItem = orderItem.getPricePerItem();
    }
}