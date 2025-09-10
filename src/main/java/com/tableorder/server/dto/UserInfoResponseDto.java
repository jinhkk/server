package com.tableorder.server.dto;


import com.tableorder.server.entity.User;
import com.tableorder.server.entity.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserInfoResponseDto {
    private Integer id;
    private String userName;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt;

    public UserInfoResponseDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.role = user.getUserRole();
        this.createdAt = user.getCreatedAt();
    }
}
