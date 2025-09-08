package com.tableorder.server.repository;

import com.tableorder.server.dto.DailySalesResponseDto; // DTO import 추가
import com.tableorder.server.dto.MonthlySalesResponseDto;
import com.tableorder.server.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // @Query import 추가
import org.springframework.data.repository.query.Param; // @Param import 추가

import java.time.LocalDate; // LocalDate import 추가
import java.util.List; // List import 추가

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    
    // 일 매출 조회
    @Query("SELECT new com.tableorder.server.dto.DailySalesResponseDto(CAST(o.createdAt AS LocalDate), SUM(o.totalPrice)) " +
            "FROM Orders o " +
            "WHERE CAST(o.createdAt AS LocalDate) BETWEEN :startDate AND :endDate " +
            "AND o.status != com.tableorder.server.entity.OrderStatus.CANCELED " +
            "GROUP BY CAST(o.createdAt AS LocalDate) " +
            "ORDER BY CAST(o.createdAt AS LocalDate) ASC")
    List<DailySalesResponseDto> findDailySalesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 월 매출 조회
    @Query(value = "SELECT " +
            "    DATE_FORMAT(created_at, '%Y-%m') AS saleMonth, " +
            "    SUM(total_price) AS monthlySales " +
            "FROM " +
            "    orders " +
            "WHERE " +
            "    YEAR(created_at) = :year " +
            "    AND status != 'CANCELED' " +
            "GROUP BY " +
            "    saleMonth " +
            "ORDER BY " +
            "    saleMonth ASC",
            nativeQuery = true)
    List<MonthlySalesResponseDto> findMonthlySalesByYear(@Param("year") int year);

}


// @Query : 쿼리문 작성하게 만들어주는 기능을 담당한다