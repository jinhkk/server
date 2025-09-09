package com.tableorder.server.controller;

import com.tableorder.server.dto.AddNewMenuDto;
import com.tableorder.server.dto.MenuItemResponseDto;
import com.tableorder.server.dto.UpdateMenuItemDto;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')") // 관리자나 직원만 수정 가능
    @PutMapping("/menu-items/{menuItemId}")
    public ResponseEntity<MenuItemResponseDto> updateMenu(
            @PathVariable Integer menuItemId,
            @Valid @RequestBody UpdateMenuItemDto requestDto) {

        // 1. 서비스에 메뉴 수정을 위임한다.
        MenuItem updatedMenuItem = service.updateMenu(menuItemId, requestDto);

        // 2. DB에 반영된 최신 정보를 다시 DTO로 변환해서 클라이언트에게 보내준다.
        MenuItemResponseDto responseDto = new MenuItemResponseDto(updatedMenuItem);

        // 3. 성공했다는 의미로 HTTP 상태 코드 200(OK)와 함께 응답한다.
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')") // 관리자나 직원만 삭제 가능
    @DeleteMapping("/menu-items/{menuItemId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Integer menuItemId) {
        // 1. 서비스에 메뉴 삭제를 위임한다.
        service.deleteMenu(menuItemId);

        // 2. 성공적으로 삭제되었다는 메시지를 클라이언트에게 보내준다.
        //    삭제 후에는 돌려줄 데이터가 없으니, 보통은 메시지만 보내거나
        //    아무 내용 없이(No Content) 응답하기도 해.
        String successMessage = menuItemId + "번 메뉴가 성공적으로 삭제되었습니다.";

        return ResponseEntity.ok(successMessage);
    }

}
