package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantActivationStateInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Restaurants")
public interface RestaurantActivationControllerOpenApi {
    @Operation(summary = "Update restaurant activation state",
            description = "Activates or deactivates the specified restaurant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Activation state updated"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<Void> activateResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Whether the restaurant should be active", required = true)
            RestaurantActivationStateInputDTO restaurantActivationStateInput);
}
