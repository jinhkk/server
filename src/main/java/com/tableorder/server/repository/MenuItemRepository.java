package com.tableorder.server.repository;

import com.tableorder.server.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByCategoryId(Integer categoryId); // MenuItem : 어떤 클래스(데이터, 테이블)를 전문적으로 다룰것이냐,
                                                                                // Integer : row 찾을때 무엇으로 찾을거냐? 기본키인 id가 int라서 Integer

}
