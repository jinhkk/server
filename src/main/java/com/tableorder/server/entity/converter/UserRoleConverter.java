package com.tableorder.server.entity.converter;


import com.tableorder.server.entity.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter // Converter 임을 선언
public class UserRoleConverter implements AttributeConverter<UserRole, String> {


    // Enum을 데이터베이스 컬럼 값(String)으로 변환
    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
            // UserRole.ADMIN -> "admin" (소문자)
        return userRole.getRole();
    }

    @Override
    public UserRole convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        for (UserRole role : UserRole.values()) {
            if (role.getRole().equals(s)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role : " + s);
    }
}
