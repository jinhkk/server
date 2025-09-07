package com.tableorder.server.service;

import com.tableorder.server.dto.MenuItemResponseDto;
import com.tableorder.server.entity.MenuItem;
import com.tableorder.server.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuItemRepository repo;

    public MenuService(MenuItemRepository repo) {
        this.repo = repo;
    }

    public List<MenuItemResponseDto> findMenuItemByCategory(Integer categoryId) {
        List<MenuItem> menuItems = repo.findByCategoryId(categoryId);

        // 조회한 엔티티 리스트를 DTO 리스트로 변환
        return menuItems.stream()
                .map(MenuItemResponseDto::new)
                .collect(Collectors.toList());
    }
}