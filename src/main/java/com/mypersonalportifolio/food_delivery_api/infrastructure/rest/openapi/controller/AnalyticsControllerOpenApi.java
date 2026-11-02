package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesFilterDTO;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.AnalyticsController.AnalyticsRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "Analytics", description = "Endpoints for restaurant sales analytics and reports")
public interface AnalyticsControllerOpenApi {
    @Operation(summary = "Get daily sales analytics",
            description = "Returns sales totals grouped by day for a restaurant and optional date/time-zone filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Daily sales data returned")
    })
    List<DailySalesProjectionDTO> getDailySales(
            @Parameter(description = "Optional date range and UTC offset filters") DailySalesFilterDTO filter,
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId);

    @Operation(summary = "Download daily sales report as PDF",
            description = "Downloads a PDF report of daily sales for a restaurant and optional date/time-zone filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "PDF report generated"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<byte[]> getDailySalesInPdf(
            @Parameter(description = "Optional date range and UTC offset filters") DailySalesFilterDTO filter,
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId);

    @Operation(summary = "Discover analytics endpoints",
            description = "Returns links to the available analytics resources.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Analytics links returned")
    })
    ResponseEntity<AnalyticsRepresentationModel> getDiscoverability();
}
