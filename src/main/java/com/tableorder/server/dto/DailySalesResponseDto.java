package com.tableorder.server.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class DailySalesResponseDto {
    private LocalDate saleDate;
    private BigDecimal dailySales;
    
    // JPA가 쿼리 결과를 DTO로 바로 매핑하기 위해 생성자 생성
    public DailySalesResponseDto(LocalDate saleDate, BigDecimal dailySales) {
        this.saleDate = saleDate;
        this.dailySales = dailySales;
    }
}
