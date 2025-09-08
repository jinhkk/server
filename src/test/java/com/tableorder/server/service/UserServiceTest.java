package com.tableorder.server.service;

import com.tableorder.server.dto.SignUpRequestDto;
import com.tableorder.server.entity.User;
import com.tableorder.server.entity.UserRole;
import com.tableorder.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach; // import 추가
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // ▼▼▼▼▼ 이 메소드를 통째로 추가해주세요! ▼▼▼▼▼
    @BeforeEach
    void setUp() {
        // 각 테스트가 실행되기 전에 DB를 초기화
        userRepository.deleteAll();
    }
    // ▲▲▲▲▲ 여기까지 추가! ▲▲▲▲▲

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        // given
        SignUpRequestDto requestDto = new SignUpRequestDto("testuser", "password123", "테스트유저", UserRole.STAFF);

        // when
        userService.signup(requestDto);

        // then
        assertEquals(1, userRepository.count());
        User foundUser = userRepository.findByUserName("testuser").orElse(null);
        assertNotNull(foundUser);
        assertEquals(UserRole.STAFF, foundUser.getUserRole());
    }

    @Test
    @DisplayName("중복된 아이디로 회원가입 시 예외 발생")
    void signup_fail_when_username_is_duplicated() {
        // given
        SignUpRequestDto existingUserDto = new SignUpRequestDto("admin", "password123", "관리자", UserRole.ADMIN);
        userService.signup(existingUserDto);

        // when
        SignUpRequestDto newUserDto = new SignUpRequestDto("admin", "password456", "새로운 관리자", UserRole.ADMIN);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(newUserDto);
        });
    }
}