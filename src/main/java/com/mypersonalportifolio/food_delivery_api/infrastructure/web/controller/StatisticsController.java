package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessDailySalesStatsUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesQueryProjection;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistics")
@RestController
public class StatisticsController {

    @Autowired
    ProcessDailySalesStatsUseCase processDailySalesStatsUseCase;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/daily-sales")
    public List<DailySalesProjectionDTO> getDailySales(@Valid DailySalesQueryProjection.FilterDTO filter){
        return processDailySalesStatsUseCase.execute(filter);
    }
}
