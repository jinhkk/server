package com.tableorder.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor; // Lombok import 추가

import java.math.BigDecimal;

@AllArgsConstructor // 모든 arg 포함하는 생성자
@Getter
@NoArgsConstructor // 기본 생성자 만들어줌
public class AddNewMenuDto {

    @NotBlank(message = "메뉴 이름은 필수입니다.") // 비어있으면 안 된다는 유효성 검사
    private String name;

    @NotNull(message = "메뉴 가격은 필수입니다.") // Null이면 안 된다는 유효성 검사
    @Positive(message = "가격은 0보다 커야 합니다.") // 반드시 양수여야 한다는 유효성 검사
    private BigDecimal price;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Integer categoryId;

    private String description;
    private String imageUrl;

//    public AddNewMenuDto(String name, BigDecimal price, Integer categoryId, String description, String imageUrl) {
//        this.name = name;
//        this.price = price;
//        this.categoryId = categoryId;
//        this.description = description;
//        this.imageUrl = imageUrl;
//    }
}