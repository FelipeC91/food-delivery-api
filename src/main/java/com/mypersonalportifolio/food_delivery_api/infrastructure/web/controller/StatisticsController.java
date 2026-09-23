package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessDailySalesReportPdfUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessDailySalesStatsUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesQueryProjection;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    ProcessDailySalesReportPdfUseCase processDailySalesReportPdfUseCase;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySalesProjectionDTO> getDailySales(@Valid DailySalesQueryProjection.FilterDTO filter){
        return processDailySalesStatsUseCase.execute(filter);
    }


    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getDailySalesInPdf(DailySalesQueryProjection.FilterDTO filter){
        var dailySalesPdf = processDailySalesReportPdfUseCase.execute(filter);

        var header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_PDF)
                            .headers(header)
                            .body(dailySalesPdf);
    }
}
