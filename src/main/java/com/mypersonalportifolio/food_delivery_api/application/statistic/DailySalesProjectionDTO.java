package com.mypersonalportifolio.food_delivery_api.application.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@AllArgsConstructor
public class DailySalesProjectionDTO {
    private Date creationDate;
    private Long totalSales;
    private BigDecimal totalBilled;
}
