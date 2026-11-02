package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Restaurants")
public interface RestaurantManagersControllerOpenApi {
    @Operation(summary = "List restaurant managers", description = "Returns the users assigned as managers of a restaurant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurant managers returned"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<CollectionModel<UserRepresentationModel>> getResourceId(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId);

    @Operation(summary = "Add a restaurant manager", description = "Assigns a user as a manager of the specified restaurant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Manager assigned"),
            @ApiResponse(responseCode = "404", description = "Restaurant or user not found")
    })
    void attachResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(description = "User identifier to assign as manager", required = true) UUID userId);

    @Operation(summary = "Remove a restaurant manager", description = "Removes the specified user from the restaurant's managers.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Manager removed"),
            @ApiResponse(responseCode = "404", description = "Restaurant or user not found")
    })
    void detachResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantTargetId,
            @Parameter(description = "User identifier to remove", required = true) UUID userId);
}
