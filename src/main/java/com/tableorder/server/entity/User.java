package com.tableorder.server.entity;

import com.tableorder.server.entity.converter.UserRoleConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Convert(converter = UserRoleConverter.class) // Converter 사용함을 알림
    @Column(name = "role", nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private String name;
}
