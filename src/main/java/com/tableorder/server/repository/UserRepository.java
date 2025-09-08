package com.tableorder.server.repository;

import com.tableorder.server.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(@NotBlank(message = "사용자 이름은 필수입니다.") @Size(min = 4, max = 50, message = "사용자 이름은 4글자 이상 50글자 이하로 입력해주세요.") String userName);
}
