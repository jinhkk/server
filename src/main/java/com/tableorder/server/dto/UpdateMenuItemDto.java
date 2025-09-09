// src/main/java/com/tableorder/server/dto/UpdateMenuItemDto.java

package com.tableorder.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor // JSON을 객체로 변환할 때 Jackson 라이브러리가 기본 생성자를 필요로 해
public class UpdateMenuItemDto {

    @NotBlank(message = "메뉴 이름은 비워둘 수 없습니다.")
    private String name;

    @NotNull(message = "메뉴 가격은 필수입니다.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    @NotNull(message = "카테고리 ID는 필수입니다.")
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