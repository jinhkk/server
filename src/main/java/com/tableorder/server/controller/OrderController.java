package com.tableorder.server.controller;

import com.tableorder.server.dto.AggregatedOrderItemDto;
import com.tableorder.server.dto.OrderRequestDto;
import com.tableorder.server.dto.TableOrderResponseDto;
import com.tableorder.server.dto.UnpaidOrderResponseDto;
import com.tableorder.server.entity.Orders;
import com.tableorder.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;


    @PostMapping("/")
    public Orders createOrder(@RequestBody OrderRequestDto requestDto) {
        return service.createOrder(requestDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN, STAFF')")
    @PostMapping("/{orderId}/pay")
    public ResponseEntity<String> processPayment(@PathVariable Integer orderId){
        service.processPayment(orderId);
        return ResponseEntity.ok(orderId + "번 주문의 결제가 완료 되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/pay/table/{tableNumber}") // 테이블 번호로 결제하는 새로운 API
    public ResponseEntity<String> processPaymentForTable(@PathVariable Integer tableNumber) {
        service.processPaymentForTable(tableNumber);
        return ResponseEntity.ok(tableNumber + "번 테이블의 결제가 완료되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/unpaid")
    public ResponseEntity<List<TableOrderResponseDto>> getUnpaidOrders() {
        return ResponseEntity.ok(service.getUnpaidOrdersByTable());
    }


    @GetMapping("/table/{tableNumber}/unpaid")
    public ResponseEntity<List<AggregatedOrderItemDto>> getUnpaidOrdersForTable(@PathVariable Integer tableNumber) {
        try {
            List<AggregatedOrderItemDto> aggregatedItems = service.getUnpaidOrdersForTable(tableNumber);
            return ResponseEntity.ok(aggregatedItems);
        } catch (Exception e) {
            // 어떤 에러가 발생하더라도, 500 에러 대신 비어있는 목록과 200 OK 상태를 반환한다.
            log.error("[API ERROR] /api/orders/table/{}/unpaid 조회 중 에러 발생: {}", tableNumber, e.getMessage());
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}
