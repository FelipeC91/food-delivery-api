package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.StateRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "States", description = "Browse Brazilian states.")
public interface StateControllerOpenApi {
    @Operation(summary = "List states", description = "Returns all registered states.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "States returned"))
    CollectionModel<StateRepresentationModel> getResources();

    @Operation(summary = "Get a state", description = "Returns a state by its numeric identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State found"),
            @ApiResponse(responseCode = "404", description = "State not found")
    })
    StateRepresentationModel getSingleResource(
            @Parameter(description = "State identifier", required = true, example = "1") Long stateId);
}
