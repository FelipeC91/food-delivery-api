package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.filter.OrderSearchFilterDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.PageableQueryParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

@Tag(name = "Orders", description = "Create, search, and retrieve customer orders.")
public interface OrderControllerOpenApi {
    // TODO review request body
    @Operation(summary = "Search orders", description = "Returns a paginated list of orders matching the supplied filters.",parameters = {
        @Parameter(in = ParameterIn.QUERY, name = "customerId",
                description = "customer ID",
                example = "9509edda-f66e-47c5-849a-3080314fb7db", schema = @Schema(type = "string")),
        @Parameter(in = ParameterIn.QUERY, name = "restaurantId",
                description = "restaurant ID",
                example = "f1b460c9-dcc0-4ec3-bb72-deec95803e99", schema = @Schema(type = "string")),
        @Parameter(in = ParameterIn.QUERY, name = "createdSince",
                example = "2019-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
        @Parameter(in = ParameterIn.QUERY, name = "createdUntil",
                example = "2026-11-29T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
    })
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Orders returned"))
    @PageableQueryParameter
    PagedModel<OrderSimplifiedRepresentationModel> getAllResources(@Parameter(required = false) OrderSearchFilterDTO filter,
            @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get an order", description = "Returns an order, including its items and current status.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    OrderRepresentationModel getResource(
            @Parameter(description = "Order identifier", required = true, example = "a1111111-2222-4333-8444-555555555555") UUID orderId);

    @Operation(summary = "Create an order",
            description = "Creates an order for the authenticated customer. The customer identifier is taken from the access token.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "The order data is invalid"),
            @ApiResponse(responseCode = "404", description = "Restaurant, product, or payment method not found")
    })
    OrderRepresentationModel createResource(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order details and items", required = true)
            com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO orderInputDTO,
            @Parameter(hidden = true) org.springframework.security.oauth2.jwt.Jwt jwt);
}
