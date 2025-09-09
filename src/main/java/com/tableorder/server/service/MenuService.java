package com.tableorder.server.service;

import com.tableorder.server.dto.AddNewMenuDto;
import com.tableorder.server.dto.MenuItemResponseDto;
import com.tableorder.server.entity.Category;
import com.tableorder.server.entity.MenuItem;
import com.tableorder.server.repository.CategoryRepository;
import com.tableorder.server.repository.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuService {
    private final MenuItemRepository repo;
    private final CategoryRepository categoryRepository;

    public MenuService(MenuItemRepository repo, CategoryRepository categoryRepository, CategoryRepository categoryRepository1) {
        this.repo = repo;
        this.categoryRepository = categoryRepository1;
    }



    public List<MenuItemResponseDto> findMenuItemByCategory(Integer categoryId) {
        List<MenuItem> menuItems = repo.findByCategoryId(categoryId);

        // 조회한 엔티티 리스트를 DTO 리스트로 변환
        return menuItems.stream()
                .map(MenuItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateSoldOutStatus(Integer menuItemId, boolean isSoldOut) {
        MenuItem menuItem = repo.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

        menuItem.updateSoldOutStatus(isSoldOut);
    }
    @Transactional
    public MenuItem addNewMenu(AddNewMenuDto requestDto) {
        // 1. DTO에 담겨온 categoryId로 실제 Category 엔티티를 DB에서 찾아옵니다.
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다: " + requestDto.getCategoryId()));

        // 2. DTO와 찾아온 Category를 사용해 새로운 MenuItem 엔티티를 만듭니다.
        MenuItem newMenuItem = MenuItem.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .category(category) // DB에서 찾아온 Category 엔티티를 연결
                .description(requestDto.getDescription())
                .imageUrl(requestDto.getImageUrl())
                .isSoldOut(false) // 새 메뉴는 기본적으로 '판매중' 상태
                .build();

        // 3. 완성된 MenuItem 엔티티를 DB에 저장하고, 저장된 객체를 반환합니다.
        return repo.save(newMenuItem);
    }
}