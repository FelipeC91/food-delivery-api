package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PermissionRepresentationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@Tag(name = "Permissions", description = "Browse the permissions available in the system.")
public interface PermissionControllerOpenApi {
    @Operation(summary = "List permissions", description = "Returns a paginated list of system permissions.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Permissions returned"))
    PagedModel<PermissionRepresentationModel> listAllResources(@Parameter(hidden = true) Pageable pageable, Authentication authentication);

    @Operation(summary = "Get a permission", description = "Returns a permission by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission found"),
            @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    ResponseEntity<?> getResourceById(
            @Parameter(description = "Permission identifier", required = true) UUID permissionId, Authentication authentication);
}
