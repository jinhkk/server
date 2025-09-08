package com.tableorder.server.dto;

import com.tableorder.server.entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 프론트엔드(Flutter 앱)에서 주문 요청을 보낼 때 사용하는 데이터 형식(DTO)입니다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    // 주문하는 테이블 번호
    private Integer tableNumber;

    // 주문하는 메뉴 아이템 목록
    private List<OrderItemRequest> orderItems;

    /**
     * 주문 요청에 포함될 개별 메뉴 아이템의 데이터 형식입니다.
     * 외부에 노출될 필요가 없어 정적 내부 클래스(static nested class)로 정의했습니다.
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemRequest {

        // 주문할 메뉴의 ID
        private Integer menuItemId;

        // 주문할 수량
        private Integer quantity;
    }

}