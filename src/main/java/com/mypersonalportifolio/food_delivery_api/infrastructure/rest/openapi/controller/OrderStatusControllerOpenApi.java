package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderStatusInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Orders")
public interface OrderStatusControllerOpenApi {
    @Operation(summary = "Update order status", description = "Sets the specified order to the requested status.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order status updated"),
            @ApiResponse(responseCode = "400", description = "The requested status is invalid"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<Void> updateResourceProperty(
            @Parameter(description = "Order identifier", required = true) UUID orderId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New order status", required = true)
            OrderStatusInputDTO orderStatusInputDTO);
}
