package com.tableorder.server.dto;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class SalesByMenuResponseDto {
    private String menuName;
    private Long quantitySold;
    private BigDecimal totalSales;

    // JPA 쿼리 결과로부터 직접 객체를 생성하기 위한 생성자
    public SalesByMenuResponseDto(String menuName, Long quantitySold, BigDecimal totalSales) {
        this.menuName = menuName;
        this.quantitySold = quantitySold;
        this.totalSales = totalSales;
    }
}