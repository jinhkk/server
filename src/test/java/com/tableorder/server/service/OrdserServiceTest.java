package com.tableorder.server.service;

import com.tableorder.server.dto.OrderRequestDto;
import com.tableorder.server.entity.*;
import com.tableorder.server.repository.CategoryRepository;
import com.tableorder.server.repository.MenuItemRepository;
import com.tableorder.server.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem soldOutMenuItem;

    @BeforeEach
    void setUp() {
        // 테스트를 실행하기 전에 필요한 기본 메뉴 데이터를 미리 만들어 둡니다.
        Category category = categoryRepository.save(Category.builder().name("테스트 카테고리").build());

        menuItem1 = menuItemRepository.save(MenuItem.builder()
                .name("테스트메뉴1")
                .price(new BigDecimal("10000.00"))
                .category(category)
                .isSoldOut(false)
                .build());

        menuItem2 = menuItemRepository.save(MenuItem.builder()
                .name("테스트메뉴2")
                .price(new BigDecimal("5000.00"))
                .category(category)
                .isSoldOut(false)
                .build());

        soldOutMenuItem = menuItemRepository.save(MenuItem.builder()
                .name("품절메뉴")
                .price(new BigDecimal("7000.00"))
                .category(category)
                .isSoldOut(true) // 이 메뉴는 품절 상태입니다.
                .build());
    }

    @Test
    @DisplayName("주문 생성 및 총액 계산 성공")
    void createOrder_success() {
        // given - 테스트메뉴1(10,000원) 2개와 테스트메뉴2(5,000원) 1개를 주문
        OrderRequestDto.OrderItemRequest item1 = new OrderRequestDto.OrderItemRequest(menuItem1.getId(), 2);
        OrderRequestDto.OrderItemRequest item2 = new OrderRequestDto.OrderItemRequest(menuItem2.getId(), 1);
        OrderRequestDto requestDto = new OrderRequestDto(3, List.of(item1, item2));

        // when - 주문 생성 기능을 실행하면
        Orders createdOrder = orderService.createOrder(requestDto);

        // then - 이런 결과가 나와야 한다.
        assertNotNull(createdOrder.getId()); // 주문이 DB에 저장되어 ID가 생성되었는지 확인
        assertEquals(2, createdOrder.getOrderItems().size()); // 주문 상세 항목이 2개인지 확인

        // 총액 계산 검증: (10,000 * 2) + (5,000 * 1) = 25,000
        assertEquals(0, new BigDecimal("25000.00").compareTo(createdOrder.getTotalPrice()));
    }

    @Test
    @DisplayName("품절된 메뉴 주문 시 예외 발생")
    void createOrder_fail_when_menu_is_sold_out() {
        // given - 품절된 메뉴를 주문하는 요청
        OrderRequestDto.OrderItemRequest soldOutItem = new OrderRequestDto.OrderItemRequest(soldOutMenuItem.getId(), 1);
        OrderRequestDto requestDto = new OrderRequestDto(5, List.of(soldOutItem));

        // when & then - 주문 생성 기능을 실행하면 IllegalStateException 예외가 발생하는 것을 기대한다.
        assertThrows(IllegalStateException.class, () -> {
            orderService.createOrder(requestDto);
        });
    }
}