package com.tableorder.server.controller;

import com.tableorder.server.dto.DailySalesResponseDto;
import com.tableorder.server.dto.MonthlySalesResponseDto;
import com.tableorder.server.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/daily")
    public ResponseEntity<List<DailySalesResponseDto>> getDailySales(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<DailySalesResponseDto> dailySales = service.getDailySales(startDate, endDate);
        return ResponseEntity.ok(dailySales);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/monthly")
    public ResponseEntity<List<MonthlySalesResponseDto>> getMonthlySales(@RequestParam("year") int year) {
        List<MonthlySalesResponseDto> monthlySales = service.getMonthlySales(year);
        return ResponseEntity.ok(monthlySales);
    }

}
