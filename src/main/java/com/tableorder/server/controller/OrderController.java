package com.tableorder.server.controller;

import com.tableorder.server.dto.OrderRequestDto;
import com.tableorder.server.entity.Orders;
import com.tableorder.server.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/orders")
    public Orders createOrder(@RequestBody OrderRequestDto requestDto) {
        return service.createOrder(requestDto);
    }

}
