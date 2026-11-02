package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.RootEntryPointController.RootEntryPointRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

public interface RootEntryPointControllerOpenApi {
    @Operation(hidden = true)
    ResponseEntity<RootEntryPointRepresentationModel> rootEntryPoint();
}
