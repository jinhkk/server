package com.tableorder.server.controller;


import com.tableorder.server.dto.LoginRequestDto;
import com.tableorder.server.dto.SignUpRequestDto;
import com.tableorder.server.dto.UserInfoResponseDto;
import com.tableorder.server.security.UserDetailsImpl;
import com.tableorder.server.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequestDto requestDto) {
        // @Valid 이게 유효성 검사 하게하는거임 아까꺼 써봤자 이거 없으면 동작 안함
        // @RequestBody 이거 제이선으로 받아도 DTO로 변환함
        service.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        String token = service.login(requestDto);
        response.addHeader("Authorization", token);  // 이게 헤더로 토큰을 보여주는거임
        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoResponseDto userInfo = service.getMyInfo(userDetails.getUser());
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserInfoResponseDto>> getAllUers() {
        List<UserInfoResponseDto> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')") // 오직 ADMIN 권한만 이 기능을 사용할 수 있습니다.
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        service.deleteUser(userId);
        return ResponseEntity.ok(userId + "번 사용자가 성공적으로 삭제되었습니다.");
    }
}
