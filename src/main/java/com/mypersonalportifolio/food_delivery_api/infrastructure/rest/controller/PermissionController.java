package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.model.PermissionEncapsulation;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.PermissionService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PermissionRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PermissionRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller.PermissionControllerOpenApi;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.authorization_server.CustomUserDetails;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.CustomSecurityPolicies;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.JwtClaimNames;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
public class PermissionController implements PermissionControllerOpenApi {

    private final PermissionRepository permissionRepository;
    private final PermissionRepresentationModelAssembler assembler;
    private final PagedResourcesAssembler<Permission> pagedResourcesAssembler;
    private final CustomSecurityPolicies customSecurityPolicies;
    private final PermissionService permissionService;

    public PermissionController(PermissionRepository permissionRepository, PermissionRepresentationModelAssembler assembler, PagedResourcesAssembler<Permission> pagedResourcesAssembler, CustomSecurityPolicies customSecurityPolicies, PermissionService permissionService) {
        this.permissionRepository = permissionRepository;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.customSecurityPolicies = customSecurityPolicies;
        this.permissionService = permissionService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PagedModel<PermissionRepresentationModel> listAllResources(@PageableDefault(size = 10) Pageable pageable,
                                                                      Authentication authentication) {

        if (customSecurityPolicies.hasAccessToProtectedPermissions(authentication))
            return pagedResourcesAssembler.toModel(permissionRepository.findAll(pageable), assembler);


        return pagedResourcesAssembler.toModel(permissionRepository.findByEncapsulation(PermissionEncapsulation.PUBLIC, pageable), assembler);
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<?> getResourceById(@PathVariable UUID permissionId,
                                          Authentication authentication) {
        var permission = permissionService.findValidPermission(permissionId);

        if (permission.getEncapsulation().equals(PermissionEncapsulation.PROTECTED)) {
            return customSecurityPolicies.hasAccessToProtectedPermissions(authentication)
                    ? ResponseEntity.ok(assembler.toModel(permission))
                    : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        else
            return ResponseEntity.ok(assembler.toModel(permission));

    }
}
