package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.ProductRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Products")
public interface RestaurantProductControllerOpenApi {
    @Operation(summary = "List restaurant products",
            description = "Returns a restaurant's products, optionally limited to active products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products returned"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<CollectionModel<ProductRepresentationModel>> listResources(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(description = "When true, returns only active products", required = false) Boolean onlyActive);

    @Operation(summary = "Get a restaurant product",
            description = "Returns a product by identifier from the specified restaurant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Restaurant or product not found")
    })
    ResponseEntity<ProductRepresentationModel> findOneResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(description = "Product identifier", required = true) UUID productId);

    @Operation(summary = "Create a restaurant product", description = "Creates a product in the specified restaurant's catalog.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "The product data is invalid"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<ProductRepresentationModel> createResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product to create", required = true)
            Product productCandidate);

    @Operation(summary = "Associate an existing product",
            description = "Associates an existing product with the specified restaurant.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product associated with restaurant"),
            @ApiResponse(responseCode = "400", description = "The product data is invalid"),
            @ApiResponse(responseCode = "404", description = "Restaurant or product not found")
    })
    ResponseEntity<ProductRepresentationModel> associateResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product to associate", required = true)
            Product productCandidate);
}
