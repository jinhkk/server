package com.tableorder.server.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_items")
public class MenuItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price; // BigDecimal : 숫자를 2진수로 바꿔서 계산하는 게 아니라, 우리가 종이에 쓰듯이 10진법 계산을 그대로 흉내냄

    private String imageUrl;

    // --- 👇 여기가 수정된 핵심 부분입니다! ---
    @Builder.Default // @Builder.Default는
    @Column(nullable = false)
    private Boolean isSoldOut = false; // 이렇게 초기값이 있는 필드와 한 세트여야 함

    public void updateSoldOutStatus(boolean isSoldOut) {
        this.isSoldOut = isSoldOut;
    }
}