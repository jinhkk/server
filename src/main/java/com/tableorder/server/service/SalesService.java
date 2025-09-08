package com.tableorder.server.service;

import com.tableorder.server.dto.DailySalesResponseDto;
import com.tableorder.server.dto.MonthlySalesResponseDto;
import com.tableorder.server.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final OrdersRepository repo;

    public List<DailySalesResponseDto> getDailySales(LocalDate startDate, LocalDate endDate) {
        return repo.findDailySalesBetweenDates(startDate, endDate);
    }

    public List<MonthlySalesResponseDto> getMonthlySales(int year) {
        return repo.findMonthlySalesByYear(year);
    }
}
