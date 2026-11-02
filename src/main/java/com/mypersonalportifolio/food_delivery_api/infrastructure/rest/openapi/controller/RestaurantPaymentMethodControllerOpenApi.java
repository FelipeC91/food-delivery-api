package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PaymentMethodRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Restaurants")
public interface RestaurantPaymentMethodControllerOpenApi {
    @Operation(summary = "List restaurant payment methods",
            description = "Returns the payment methods accepted by the specified restaurant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurant payment methods returned"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    ResponseEntity<CollectionModel<PaymentMethodRepresentationModel>> getResourceId(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId);

    @Operation(summary = "Allow a payment method", description = "Adds a payment method to the restaurant's accepted methods.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Payment method allowed"),
            @ApiResponse(responseCode = "404", description = "Restaurant or payment method not found")
    })
    ResponseEntity<Void> createResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(description = "Payment method identifier", required = true) UUID paymentMethodId);

    @Operation(summary = "Remove a restaurant payment method",
            description = "Removes a payment method from the restaurant's accepted methods.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Payment method removed"),
            @ApiResponse(responseCode = "404", description = "Restaurant or payment method not found")
    })
    ResponseEntity<Void> deletePaymentMethod(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantTargetId,
            @Parameter(description = "Payment method identifier", required = true) UUID paymentMethodId);
}
