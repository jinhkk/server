package com.tableorder.server.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "categories")
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID 자동 증가 설정
    private Integer id;
    
    @Column(nullable = false, unique = true)  // name 컬럼은 null 불가, 중복 불가
    private String name;
//    private LocalDateTime created_at;  // DateTime 으로 DB 만들어서 이걸로함 1:1 매치 가능 BaseTimeEntity에서 시간 상속 받아서 스근하게 주석처리
}
