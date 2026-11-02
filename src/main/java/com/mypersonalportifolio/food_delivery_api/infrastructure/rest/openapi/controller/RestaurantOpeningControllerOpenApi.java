package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantOpeningStateInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Restaurants")
public interface RestaurantOpeningControllerOpenApi {
    @Operation(summary = "Update restaurant opening state",
            description = "Opens or closes the specified restaurant for order placement.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Opening state updated"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<Void> activateResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Whether the restaurant should be open", required = true)
            RestaurantOpeningStateInputDTO restaurantOpeningStateInput);
}
