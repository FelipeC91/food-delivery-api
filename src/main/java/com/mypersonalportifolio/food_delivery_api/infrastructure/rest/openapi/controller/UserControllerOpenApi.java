package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.UserPasswordInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Users", description = "Register and manage user accounts.")
public interface UserControllerOpenApi {
    @Operation(summary = "List users", description = "Returns all registered users.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Users returned"))
    CollectionModel<UserRepresentationModel> listAllResources();

    @Operation(summary = "Get a user", description = "Returns a user by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<UserRepresentationModel> findOneResource(
            @Parameter(description = "User identifier", required = true) UUID userId);

    @Operation(summary = "Register a user", description = "Creates a new user account.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "The user data is invalid")
    })
    UserRepresentationModel createResource(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User account data", required = true)
            User userCandidate);

    @Operation(summary = "Update a user", description = "Updates the specified user's account details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "The user data is invalid"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<UserRepresentationModel> updateResource(
            @Parameter(description = "User identifier", required = true) UUID userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user data", required = true)
            User userSource);

    @Operation(summary = "Update a user's password", description = "Changes the user's password after verifying the current password.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password updated"),
            @ApiResponse(responseCode = "400", description = "The supplied password data is invalid"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<UserRepresentationModel> updatePassword(
            @Parameter(description = "User identifier", required = true) UUID userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Current and new password", required = true)
            UserPasswordInputDTO passwordSource);

    @Operation(summary = "Delete a user", description = "Deletes the specified user account.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<Void> deleteResource(
            @Parameter(description = "User identifier", required = true) UUID userId);
}
