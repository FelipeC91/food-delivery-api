package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.PageableQueryParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

@Tag(name = "Restaurant orders", description = "Retrieve orders for a restaurant.")
public interface RestaurantOrderControllerOpenApi {

    @Operation(summary = "Get restaurant orders", description = "Returns a paginated list of orders associated with a restaurant.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Restaurant orders returned"))
    @PageableQueryParameter
    PagedModel<OrderSimplifiedRepresentationModel> getRestaurantOrders(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable);
}
