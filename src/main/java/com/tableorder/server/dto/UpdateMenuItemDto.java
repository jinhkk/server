// src/main/java/com/tableorder/server/dto/UpdateMenuItemDto.java

package com.tableorder.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UpdateMenuItemDto {

    private String name;
    private BigDecimal price;
    private Integer categoryId;
    private String description;
    private String imageUrl;

    // 테스트나 다른 용도를 위해 모든 필드를 받는 생성자도 하나 만들어두면 편해
    public UpdateMenuItemDto(String name, BigDecimal price, Integer categoryId, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}