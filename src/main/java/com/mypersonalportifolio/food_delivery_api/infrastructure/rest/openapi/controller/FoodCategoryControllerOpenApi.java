package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.FoodCategoryRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Food Categories", description = "Manage food categories used by restaurants and products.")
public interface FoodCategoryControllerOpenApi {
    @Operation(summary = "List food categories", description = "Returns a paginated list of food categories.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Food categories returned"))
    PagedModel<FoodCategoryRepresentationModel> listAllResources(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get a food category", description = "Returns a food category by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food category found"),
            @ApiResponse(responseCode = "404", description = "Food category not found")
    })
    ResponseEntity<FoodCategoryRepresentationModel> findSingleResource(
            @Parameter(description = "Food category identifier", required = true) UUID foodCategoryId);

    @Operation(summary = "Create a food category", description = "Creates a food category.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Food category created"),
            @ApiResponse(responseCode = "400", description = "The food category data is invalid")
    })
    FoodCategoryRepresentationModel createResource(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Food category to create", required = true)
            FoodCategory foodCategoryCandidate);

    @Operation(summary = "Update a food category", description = "Replaces the specified food category's data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food category updated"),
            @ApiResponse(responseCode = "404", description = "Food category not found")
    })
    ResponseEntity<?> updateResource(
            @Parameter(description = "Food category identifier", required = true) UUID foodCategoryTargetId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated food category", required = true)
            FoodCategory foodCategorySource);

    @Operation(summary = "Delete a food category", description = "Deletes the specified food category.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Food category deleted"),
            @ApiResponse(responseCode = "404", description = "Food category not found")
    })
    ResponseEntity<?> deleteResource(
            @Parameter(description = "Food category identifier", required = true) UUID foodCategoryTargetId);
}
