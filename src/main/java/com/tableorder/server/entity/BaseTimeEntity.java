package com.tableorder.server.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 이 클래스를 상속받는 클래스는 아래 필드들을 컬럼으로 인식 이 클래스는 테이블로 직접 만들지 말고, 자식 클래스에게 필드만 물려주라는 뜻
@EntityListeners(AuditingEntityListener.class) //Auditing 리스너 추가한거임 시간의 변경을 감지하는 리스너
public class BaseTimeEntity {

    @CreatedDate  // 데이터가 처음 저장될 때(INSERT)의 시간을 자동으로 기록
    private LocalDateTime createdAt;

    @LastModifiedDate //데이터가 수정될 때(UPDATE)의 시간을 자동으로 기록
    private LocalDateTime updatedAt;
}
