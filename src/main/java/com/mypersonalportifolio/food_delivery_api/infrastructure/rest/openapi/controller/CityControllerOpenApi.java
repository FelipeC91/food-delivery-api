package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.CityInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.CityRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.PageableQueryParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cities", description = "Endpoints to manage city registrations")
public interface CityControllerOpenApi {
    @Operation(summary = "List cities", description = "Returns a paginated list of registered cities.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cities returned successfully")
    })
    @PageableQueryParameter
    PagedModel<CityRepresentationModel> listAllResources(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get a city by ID", description = "Returns the city matching the supplied identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City found"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    ResponseEntity<CityRepresentationModel> findOneResource(
            @Parameter(description = "City identifier", required = true, example = "1") Long cityId);

    @Operation(summary = "Create a city", description = "Registers a city using its name and the identifier of its state.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "City created"),
            @ApiResponse(responseCode = "400", description = "The city data is invalid")
    })
    ResponseEntity<?> createResource(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "City name and state identifier", required = true)
            CityInputDTO cityCandidate);

    @Operation(summary = "Update a city", description = "Replaces the name and state of the specified city.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City updated"),
            @ApiResponse(responseCode = "400", description = "The city data is invalid"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    CityRepresentationModel updateResource(
            @Parameter(description = "City identifier", required = true, example = "2") Long cityId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated city name and state identifier", required = true)
            CityInputDTO citySource);

    @Operation(summary = "Delete a city", description = "Deletes the specified city.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "City deleted"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    ResponseEntity<City> deleteResource(
            @Parameter(description = "City identifier", required = true, example = "2") Long cityId);
}
