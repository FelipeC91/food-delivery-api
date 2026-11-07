package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PermissionRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@Tag(name = "Group Permissions", description = "Manage permissions assigned to user groups.")
public interface GroupPermissionControllerOpenApi {
    @Operation(summary = "List group permissions", description = "Returns the permissions assigned to a user group.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group permissions returned"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    ResponseEntity<CollectionModel<PermissionRepresentationModel>> listPermissions(
            @Parameter(description = "Group identifier", required = true) UUID userGroupId);

    @Operation(summary = "Grant a permission to a group", description = "Associates a permission with the specified group.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permission granted"),
            @ApiResponse(responseCode = "404", description = "Group or permission not found")
    })
    ResponseEntity<Void> associateResource(
            @Parameter(description = "Group identifier", required = true) UUID userGroupId,
            @Parameter(description = "Permission identifier", required = true) UUID permissionId,
            Authentication authentication);

    @Operation(summary = "Revoke a permission from a group", description = "Removes a permission from the specified group.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permission revoked"),
            @ApiResponse(responseCode = "404", description = "Group or permission not found")
    })
    ResponseEntity<Void> detachResource(
            @Parameter(description = "Group identifier", required = true) UUID userGroupId,
            @Parameter(description = "Permission identifier", required = true) UUID permissionId,
            Authentication authentication);
}
