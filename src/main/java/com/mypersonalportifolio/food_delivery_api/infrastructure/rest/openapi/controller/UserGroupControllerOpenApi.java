package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserGroupRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "User Groups", description = "Manage the groups assigned to users.")
public interface UserGroupControllerOpenApi {
    @Operation(summary = "List a user's groups", description = "Returns all groups assigned to the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User groups returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    CollectionModel<UserGroupRepresentationModel> getUserGroups(
            @Parameter(description = "User identifier", required = true) UUID userId);

    @Operation(summary = "Add a user to a group", description = "Assigns a group to the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group assigned"),
            @ApiResponse(responseCode = "404", description = "User or group not found")
    })
    ResponseEntity<Void> attachResource(
            @Parameter(description = "User identifier", required = true) UUID userId,
            @Parameter(description = "Group identifier", required = true) UUID groupId);

    @Operation(summary = "Remove a user from a group", description = "Removes a group assignment from the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group removed from user"),
            @ApiResponse(responseCode = "404", description = "User or group not found")
    })
    ResponseEntity<Void> detachResource(
            @Parameter(description = "User identifier", required = true) UUID userId,
            @Parameter(description = "Group identifier", required = true) UUID groupId);
}
