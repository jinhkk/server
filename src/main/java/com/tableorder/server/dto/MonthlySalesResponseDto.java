package com.tableorder.server.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MonthlySalesResponseDto {
    private String saleMonth;
    private BigDecimal monthlySales;

    public MonthlySalesResponseDto(String saleMonth, BigDecimal monthlySales) {
        this.saleMonth = saleMonth;
        this.monthlySales = monthlySales;
    }
}
