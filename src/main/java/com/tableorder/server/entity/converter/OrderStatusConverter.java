package com.tableorder.server.entity.converter;

import com.tableorder.server.entity.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true) // 이 Converter를 OrderStatus 타입에 자동으로 적용
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    // Enum을 데이터베이스 컬럼 값(String)으로 변환
    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        if (status == null) {
            return null;
        }
        // OrderStatus.RECEIVED -> "received" (소문자)
        return status.name().toLowerCase();
    }

    // 데이터베이스 컬럼 값(String)을 Enum으로 변환
    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        // "received" -> OrderStatus.RECEIVED
        return Stream.of(OrderStatus.values())
                .filter(s -> s.name().equalsIgnoreCase(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dbData));
    }
}