package com.tableorder.server.service;

import com.tableorder.server.dto.AddNewMenuDto;
import com.tableorder.server.dto.MenuItemResponseDto;
import com.tableorder.server.dto.UpdateMenuItemDto;
import com.tableorder.server.entity.Category;
import com.tableorder.server.entity.MenuItem;
import com.tableorder.server.repository.CategoryRepository;
import com.tableorder.server.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuItemRepository repo;
    private final CategoryRepository categoryRepository;

    public MenuItem findMenuItemById(Integer menuItemId) {
        return repo.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다: " + menuItemId));
    }

    public List<MenuItemResponseDto> findMenuItemByCategory(Integer categoryId) {

        // 1. Repository에 새로 선언한 메소드를 호출해서,
        //    "삭제 처리되지 않은" 메뉴만 DB에서 가져온다.
        List<MenuItem> menuItems = repo.findByCategoryIdAndIsDeletedFalse(categoryId);

        // 2. 가져온 엔티티 리스트를 DTO 리스트로 변환해서 반환한다.
        //    이 로직은 원래부터 여기에 있었지.
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

    @Transactional
    public MenuItem updateMenu(Integer menuItemId, UpdateMenuItemDto requestDto) {
        // 1. 수정할 메뉴를 DB에서 찾아온다. 없으면 에러 발생!
        MenuItem menuItem = repo.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다. ID: " + menuItemId));

        // 2. DTO에 담겨온 카테고리 ID로 실제 Category 엔티티를 DB에서 찾아온다.
        //    이것도 없으면 에러를 발생시켜야겠지?
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + requestDto.getCategoryId()));

        // 3. MenuItem 엔티티에 만들어둔 updateDetails 메소드를 호출해서 정보를 업데이트한다.
        menuItem.updateDetails(requestDto, category);

        // 4. @Transactional 어노테이션 덕분에, 메소드가 끝나면 JPA가 변경된 내용을 감지해서
        //    자동으로 UPDATE 쿼리를 날려준다. 그래서 repo.save()를 또 호출할 필요가 없어.
        //    (물론 명시적으로 save를 호출해도 문제는 없어)

        return menuItem; // 변경된 메뉴 정보를 반환한다.
    }

    @Transactional
    public void deleteMenu(Integer menuItemId) {
        // 1. 메뉴를 찾는다 (없으면 에러)
        MenuItem menuItem = repo.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 메뉴를 찾을 수 없습니다. ID: " + menuItemId));

        // 2. 실제 DB에서 삭제하는 대신, isDeleted 상태를 true로 변경한다.
        menuItem.delete();
        // @Transactional 덕분에 메소드가 끝나면 자동으로 UPDATE 쿼리가 날아갈 거야.
    }

}