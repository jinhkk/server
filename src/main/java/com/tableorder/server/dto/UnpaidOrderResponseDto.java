package com.tableorder.server.dto;

import com.tableorder.server.entity.Orders;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UnpaidOrderResponseDto {
    private Integer orderId;
    private LocalDateTime orderTime;
    private List<UnpaidOrderItemResponseDto> orderItems;

    public UnpaidOrderResponseDto(Orders order) {
        this.orderId = order.getId();
        this.orderTime = order.getCreatedAt();
        this.orderItems = order.getOrderItems().stream()
                .map(UnpaidOrderItemResponseDto::new)
                .collect(Collectors.toList());
    }
}