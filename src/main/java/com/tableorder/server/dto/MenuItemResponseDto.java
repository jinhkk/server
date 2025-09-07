package com.tableorder.server.dto;

import com.tableorder.server.entity.MenuItem;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class MenuItemResponseDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isSoldOut;

    // 엔티티를 DTO로 변환하는 생성자
    public MenuItemResponseDto(MenuItem menuItem) {
        this.id = menuItem.getId();
        this.name = menuItem.getName();
        this.description = menuItem.getDescription();
        this.price = menuItem.getPrice();
        this.imageUrl = menuItem.getImageUrl();
        this.isSoldOut = menuItem.getIsSoldOut();
    }
}
