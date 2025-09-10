// src/main/java/com/tableorder/server/repository/MenuItemRepository.java

package com.tableorder.server.repository;

import com.tableorder.server.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    // 기존에 있던 메소드
    List<MenuItem> findByCategoryId(Integer categoryId);

    // Soft Delete를 위해 새로 추가한 메소드. "삭제되지 않은(IsDeletedFalse) 메뉴를 카테고리 ID로 찾는다"
    // 이렇게 선언만 해두면 Spring Data JPA가 이름을 보고 마법처럼 구현해 줘.
    List<MenuItem> findByCategoryIdAndIsDeletedFalse(Integer categoryId);

//    @Query("SELECT m FROM menu_item m LEFT JOIN FETCH m.category WHERE m.id = :id AND m.isDeleted = false")
//    Optional<MenuItem> findByIdWithCategory(@Param("id") Integer id);
}