package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserGroupRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@Tag(name = "Groups", description = "Manage user groups.")
public interface GroupControllerOpenApi {
    @Operation(summary = "List groups", description = "Returns a paginated list of user groups.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Groups returned"))
    PagedModel<UserGroupRepresentationModel> listAllResources(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get a group", description = "Returns a user group by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group found"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    ResponseEntity<UserGroupRepresentationModel> findOneResource(
            @Parameter(description = "Group identifier", required = true) UUID userGroupId);

    @Operation(summary = "Create a group", description = "Creates a user group.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group created"),
            @ApiResponse(responseCode = "400", description = "The group data is invalid")
    })
    UserGroupRepresentationModel createResource(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User group to create", required = true)
            UserGroup userGroupCandidate);

    @Operation(summary = "Update a group", description = "Updates the specified group's data.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group updated"),
            @ApiResponse(responseCode = "400", description = "The group data is invalid"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    ResponseEntity<Void> updateResource(
            @Parameter(description = "Group identifier", required = true) UUID userGroupId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user group", required = true)
            UserGroup userGroupSource,
            Authentication authentication);

    @Operation(summary = "Delete a group", description = "Deletes the specified user group.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group deleted"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    ResponseEntity<Void> deleteResource(
            @Parameter(description = "Group identifier", required = true) UUID userGroupId,
            Authentication authentication);
}
