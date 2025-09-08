package com.tableorder.server.dto;


import com.tableorder.server.entity.User;
import com.tableorder.server.entity.UserRole;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private String userName;
    private String name;
    private UserRole role;

    public UserInfoResponseDto(User user) {
        this.userName = user.getUserName();
        this.name = user.getName();
        this.role = user.getUserRole();
    }
}
