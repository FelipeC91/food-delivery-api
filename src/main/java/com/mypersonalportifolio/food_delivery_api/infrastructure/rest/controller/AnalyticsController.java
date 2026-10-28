package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesFilterDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessDailySalesReportPdfUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessDailySalesAnalyticsUseCase;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesQueryProjection;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.AnalyticsLinkEngine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Analytics", description = "Endpoints to access restaurant analytics")

@RequestMapping("/analytics")
@RestController
public class AnalyticsController {

    private final ProcessDailySalesAnalyticsUseCase processDailySalesAnalyticsUseCase;
    private final ProcessDailySalesReportPdfUseCase processDailySalesReportPdfUseCase;

    public AnalyticsController(ProcessDailySalesAnalyticsUseCase processDailySalesAnalyticsUseCase,
                               ProcessDailySalesReportPdfUseCase processDailySalesReportPdfUseCase) {
        this.processDailySalesAnalyticsUseCase = processDailySalesAnalyticsUseCase;
        this.processDailySalesReportPdfUseCase = processDailySalesReportPdfUseCase;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{restaurantId}/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySalesProjectionDTO> getDailySales(@Valid DailySalesFilterDTO filter,
                                                       @PathVariable UUID restaurantId){
        filter.setRestaurantId(restaurantId);

        return processDailySalesAnalyticsUseCase.execute(filter);
    }


    @GetMapping(path = "/{restaurantId}/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getDailySalesInPdf(@Valid DailySalesFilterDTO filter,
                                                     @PathVariable UUID restaurantId){
        filter.setRestaurantId(restaurantId);

        var dailySalesPdf = processDailySalesReportPdfUseCase.execute(filter);

        var header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(header)
                .body(dailySalesPdf);
    }

    @RequestMapping({"/",""})
    @GetMapping
    public ResponseEntity<AnalyticsRepresentationModel> getDiscoverability(){
        var representationModel = new AnalyticsRepresentationModel();

        representationModel.add(AnalyticsLinkEngine.slashAnalytics());

        return ResponseEntity.ok(representationModel);
    }

    private static class AnalyticsRepresentationModel  extends RepresentationModel<AnalyticsRepresentationModel> {}
}
