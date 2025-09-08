package com.tableorder.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer tableNumber;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private OrderStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<>();

    public void updateTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void complatePayment() {
        this.status = OrderStatus.PAID;
    }
}