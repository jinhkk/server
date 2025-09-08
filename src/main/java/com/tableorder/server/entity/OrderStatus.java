package com.tableorder.server.entity;

public enum OrderStatus {
    RECEIVED,   // 주문 접수
    PREPARING,  // 준비 중
    COMPLETED,  // 완료
    CANCELED,    // 취소
    PAID
}