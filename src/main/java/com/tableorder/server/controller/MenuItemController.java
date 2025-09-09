package com.tableorder.server.controller;

import com.tableorder.server.dto.AddNewMenuDto;
import com.tableorder.server.dto.MenuItemResponseDto;
import com.tableorder.server.entity.MenuItem;
import com.tableorder.server.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')") // 접근 권한
    @PatchMapping("/menu-items/{menuItemId}/sold-out") // 데이터의 일부만 수정할때 사용하는 매핑
    public ResponseEntity<String> updateSoldOutStatus(
            @PathVariable Integer menuItemId,
            @RequestBody Map<String, Boolean> request) {

        Boolean isSoldOut = request.get("isSoldOut");
        if (isSoldOut == null) {
            return ResponseEntity.badRequest().body("isSoldOut 필드가 필요합니다.");
        }

        service.updateSoldOutStatus(menuItemId, isSoldOut);

        String status = isSoldOut ? "품절" : "판매중";
        return ResponseEntity.ok(menuItemId + "번 메뉴가 " + status + " 상태로 변경되었습니다.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/menu-items")
    public ResponseEntity<MenuItemResponseDto> addNewMenu(@Valid @ RequestBody AddNewMenuDto requestDto) {
        MenuItem createdMenuItem = service.addNewMenu(requestDto);

        MenuItemResponseDto responseDto = new MenuItemResponseDto(createdMenuItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
