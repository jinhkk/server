package com.tableorder.server.service;

import com.tableorder.server.dto.OrderRequestDto;
import com.tableorder.server.entity.*;
import com.tableorder.server.repository.MenuItemRepository;
import com.tableorder.server.repository.OrdersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderService(OrdersRepository ordersRepository, MenuItemRepository menuItemRepository) {
        this.ordersRepository = ordersRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional // 이 메소드 전체를 하나의 트랜잭션으로 묶어줍니다.
    public Orders createOrder(OrderRequestDto requestDto) {

        // 1. '영수증'에 해당하는 Orders 엔티티를 먼저 생성합니다.
        Orders newOrder = Orders.builder()
                .tableNumber(requestDto.getTableNumber())
                .status(OrderStatus.RECEIVED) // 주문 상태는 'RECEIVED'로 시작
                .totalPrice(BigDecimal.ZERO) // 총액은 우선 0으로 초기화
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;

        // 2. 주문서에 적힌 각 메뉴 항목(OrderItemRequest)을 반복 처리합니다.
        for (OrderRequestDto.OrderItemRequest itemDto : requestDto.getOrderItems()) {

            // 3. menuItemId로 실제 MenuItem 엔티티를 DB에서 찾아옵니다.
            MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                    .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다: " + itemDto.getMenuItemId()));

            if(menuItem.getIsSoldOut()) {
                throw new IllegalStateException(menuItem.getName() + " 메뉴는 현재 품절입니다.");
            }
            // 4. '주문 상세 내역'에 해당하는 OrderItems 엔티티를 생성합니다.
            OrderItems orderItem = OrderItems.builder()
                    .orders(newOrder) // 방금 만든 '영수증'과 연결
                    .menuItem(menuItem) // DB에서 찾아온 메뉴와 연결
                    .quantity(itemDto.getQuantity())
                    .pricePerItem(menuItem.getPrice()) // 주문 당시의 메뉴 가격을 저장
                    .build();

            // 5. '영수증'에 '상세 내역'을 추가합니다.
            newOrder.getOrderItems().add(orderItem);

            // 6. 총액을 계산합니다. (메뉴 가격 * 수량)
            totalPrice = totalPrice.add(menuItem.getPrice().multiply(new BigDecimal(itemDto.getQuantity())));
        }

        // 7. 계산된 최종 금액을 '영수증'에 설정합니다.
        newOrder.updateTotalPrice(totalPrice); // Orders 엔티티에 totalPrice를 업데이트하는 메소드를 추가해야 합니다.

        // 8. '영수증'(Orders)을 저장합니다. cascade 옵션 덕분에 상세 내역(OrderItems)도 함께 저장됩니다.
        return ordersRepository.save(newOrder);
    }

    // 개별 주문용 선결제 시 사용하는 느낌으로 놔두자
    @Transactional
    public void processPayment(Integer orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        order.complatePayment();
    }

    // 테이블 번호 기준으로 보통의 테이블 오더처럼 집계

    @Transactional
    public void processPaymentForTable(Integer tableNumber) {
        // 결제할 주문 상태 목록 만들기
        List<OrderStatus> statusesToPay = List.of(OrderStatus.RECEIVED, OrderStatus.PREPARING, OrderStatus.COMPLETED);

        // repo에서 해당 테이블의 미결제 주문 가져오기
        List<Orders> ordersToPay = ordersRepository.findAllByTableNumberAndStatusIn(tableNumber, statusesToPay);

        if (ordersToPay.isEmpty()) {
            throw new IllegalStateException(tableNumber + "번 테이블에 결제할 주문이 없습니다.");
        }
        // 결제한 모든 주문의 상태를 결제완료로 변경
        for(Orders orders : ordersToPay) {
            orders.complatePayment();
        }
    }
}