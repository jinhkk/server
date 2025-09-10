package com.tableorder.server.dto;

import lombok.Getter;
import java.math.BigDecimal;
import java.util.List;

@Getter
public class TableOrderResponseDto {
    private int tableNumber;
    private BigDecimal totalAmount;
    private List<UnpaidOrderResponseDto> orders;

    public TableOrderResponseDto(int tableNumber, BigDecimal totalAmount, List<UnpaidOrderResponseDto> orders) {
        this.tableNumber = tableNumber;
        this.totalAmount = totalAmount;
        this.orders = orders;
    }
}