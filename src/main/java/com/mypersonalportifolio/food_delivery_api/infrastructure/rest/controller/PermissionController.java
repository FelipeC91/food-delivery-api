package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PermissionRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PermissionRepresentationModelAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionRepository permissionRepository;
    private final PermissionRepresentationModelAssembler assembler;
    private final PagedResourcesAssembler<Permission> pagedResourcesAssembler;

    public PermissionController(PermissionRepository permissionRepository, PermissionRepresentationModelAssembler assembler, PagedResourcesAssembler<Permission> pagedResourcesAssembler) {
        this.permissionRepository = permissionRepository;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PagedModel<PermissionRepresentationModel> listAllResources(@PageableDefault(size = 10) Pageable pageable) {
        return pagedResourcesAssembler.toModel(permissionRepository.findAll(pageable), assembler);
    }

    @GetMapping("/{permissionId}")
    public Permission getResourceById(@PathVariable UUID permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException(Permission.class, permissionId.toString()));
    }
}
