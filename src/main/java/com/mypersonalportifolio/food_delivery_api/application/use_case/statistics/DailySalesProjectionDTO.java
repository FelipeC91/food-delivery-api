package com.mypersonalportifolio.food_delivery_api.application.use_case.statistics;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailySalesProjectionDTO(
        LocalDate creationDate,
        Long totalSales,
        BigDecimal totalBilled
) {
}
