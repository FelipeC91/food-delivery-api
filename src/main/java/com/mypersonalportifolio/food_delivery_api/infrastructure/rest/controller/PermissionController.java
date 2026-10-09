package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Permission;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PermissionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionRepository permissionRepository;

    public PermissionController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @GetMapping
    public List<Permission> listAllResources() {
        return permissionRepository.findAll();
    }

    @GetMapping("/{permissionId}")
    public Permission getResourceById(@PathVariable UUID permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException(Permission.class, permissionId.toString()));
    }
}
