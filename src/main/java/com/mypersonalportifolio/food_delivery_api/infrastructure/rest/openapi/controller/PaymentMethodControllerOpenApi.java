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

@Tag(name = "Payment Methods", description = "Manage payment methods available to restaurants.")
public interface PaymentMethodControllerOpenApi {
    @Operation(summary = "List payment methods", description = "Returns all registered payment methods.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Payment methods returned"))
    CollectionModel<PaymentMethodRepresentationModel> listAllResources();

    @Operation(summary = "Get a payment method", description = "Returns a payment method by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment method found"),
            @ApiResponse(responseCode = "404", description = "Payment method not found")
    })
    ResponseEntity<PaymentMethodRepresentationModel> findSingleResource(
            @Parameter(description = "Payment method identifier", required = true) UUID paymentMethodId);

    @Operation(summary = "Create a payment method", description = "Registers a payment method.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment method created"),
            @ApiResponse(responseCode = "400", description = "The payment method data is invalid")
    })
    PaymentMethodRepresentationModel createResource(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment method to create", required = true)
            com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod paymentMethodCandidate);

    @Operation(summary = "Update a payment method", description = "Updates the specified payment method.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment method updated"),
            @ApiResponse(responseCode = "400", description = "The payment method data is invalid"),
            @ApiResponse(responseCode = "404", description = "Payment method not found")
    })
    ResponseEntity<PaymentMethodRepresentationModel> updateResource(
            @Parameter(description = "Payment method identifier", required = true) UUID paymentMethodId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated payment method", required = true)
            com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod paymentMethodSource);

    @Operation(summary = "Delete a payment method", description = "Deletes the specified payment method.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Payment method deleted"),
            @ApiResponse(responseCode = "404", description = "Payment method not found")
    })
    ResponseEntity<Void> deleteResource(
            @Parameter(description = "Payment method identifier", required = true) UUID paymentMethodId);
}
