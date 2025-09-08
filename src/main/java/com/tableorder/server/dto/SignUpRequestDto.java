package com.tableorder.server.dto;

import com.tableorder.server.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "사용자 이름은 필수입니다.")  // 비어있으면 안된다 비어있으면 메세지 날려줌
    @Size(min = 4, max = 50, message = "사용자 이름은 4글자 이상 50글자 이하로 입력해주세요.") // 글자 수 제한
    private String userName;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 4, message = "비밀번호는 4자 이상으로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    
    private UserRole role = UserRole.STAFF; // 기본값은 직원으로 설정

    public SignUpRequestDto(String userName, String password, String name, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
