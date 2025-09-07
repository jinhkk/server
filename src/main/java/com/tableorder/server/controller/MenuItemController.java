package com.tableorder.server.controller;

import com.tableorder.server.dto.MenuItemResponseDto;
import com.tableorder.server.entity.MenuItem;
import com.tableorder.server.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuItemController {
    private final MenuService service;

    public MenuItemController(MenuService service) {
        this.service = service;
    }

    @GetMapping("/categories/{categoryId}/menu-items")
    // 반환 타입을 List<MenuItem> 에서 List<MenuItemResponseDto> 로 변경
    public List<MenuItemResponseDto> getMenuItemsByCategory(@PathVariable Integer categoryId) {
        return service.findMenuItemByCategory(categoryId);
    }
}
