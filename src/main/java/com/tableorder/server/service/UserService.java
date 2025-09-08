package com.tableorder.server.service;

import com.tableorder.server.dto.LoginRequestDto;
import com.tableorder.server.dto.SignUpRequestDto;
import com.tableorder.server.dto.UserInfoResponseDto;
import com.tableorder.server.entity.User;
import com.tableorder.server.repository.UserRepository;
import com.tableorder.server.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final 생성자 자동으로 만들어줌
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Transactional // DB에서 데이터를 쓰는 작업이라 트랜잭션
    public void signup(SignUpRequestDto requestDto) {
        repo.findByUserName(requestDto.getUserName())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
                });

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User newUser = User.builder()
                .userName(requestDto.getUserName())
                .password(encodedPassword)
                .name(requestDto.getName())
                .userRole(requestDto.getRole())
                .build();

        repo.save(newUser);
    }

    @Transactional
    public String login(LoginRequestDto requestDto) {
        User user = repo.findByUserName(requestDto.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtUtil.createToken(user.getUserName(), user.getUserRole());
    }

    public UserInfoResponseDto getMyInfo(User user) {
        return new UserInfoResponseDto(user);
    }
}
