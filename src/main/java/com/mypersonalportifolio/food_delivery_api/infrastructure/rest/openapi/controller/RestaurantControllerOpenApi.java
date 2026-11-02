package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.PageableQueryParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Restaurants", description = "Browse and manage restaurants.")
public interface RestaurantControllerOpenApi {
    @Operation(summary = "List restaurants", description = "Returns a paginated list of restaurants.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Restaurants returned"))
    @PageableQueryParameter
    PagedModel<RestaurantSimplifiedRepresentationModel> listAllResources(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get a restaurant", description = "Returns a restaurant's full details by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurant found"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<RestaurantRepresentationModel> findSingleResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId);

    @Operation(summary = "Create a restaurant", description = "Registers a restaurant and its address.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurant created"),
            @ApiResponse(responseCode = "400", description = "The restaurant data is invalid")
    })
    ResponseEntity<RestaurantRepresentationModel> createResource(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Restaurant details and address", required = true)
            RestaurantInputDTO restaurantInputDTO);

    @Operation(summary = "Update a restaurant", description = "Replaces the specified restaurant's details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurant updated"),
            @ApiResponse(responseCode = "400", description = "The restaurant data is invalid"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<?> updateResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantTargetId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated restaurant details", required = true)
            RestaurantInputDTO restaurantInput);
}
