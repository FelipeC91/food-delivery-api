package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserGroupRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.UserGroupRepresentationModelAssembler;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupRepository groupRepository;
    private final GroupService userGroupService;
    private final UserGroupRepresentationModelAssembler assembler;

    public GroupController(GroupRepository groupRepository, GroupService userGroupService, UserGroupRepresentationModelAssembler assembler) {
        this.groupRepository = groupRepository;
        this.userGroupService = userGroupService;
        this.assembler = assembler;
    }

    @GetMapping
    public List<UserGroup> listAllResources() {
        return groupRepository.findAll();
    }

    @GetMapping("/{userGroupId}")
    public ResponseEntity<UserGroupRepresentationModel> findOneResource(@PathVariable UUID userGroupId) {
        var userGroup = assembler.toModel(userGroupService.findValidUserGroup(userGroupId));
        return ResponseEntity.ok(userGroup);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserGroupRepresentationModel createResource(@RequestBody @Valid UserGroup userGroupCandidate) {
        try {
            return assembler.toModel(groupRepository.save(userGroupCandidate));
        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(UserGroup.class, userGroupCandidate.getName());
        }
    }

    @PutMapping("/{userGroupId}")
    public ResponseEntity<UserGroupRepresentationModel> updateResource(@PathVariable UUID userGroupId,
                                                     @RequestBody @Valid UserGroup userGroupSource) {
        var userGroupTarget = userGroupService.findValidUserGroup(userGroupId);

        var updatedUserGroup = userGroupService.updateProperties(userGroupTarget, userGroupSource);

        return ResponseEntity.ok(assembler.toModel(updatedUserGroup));
    }

    @DeleteMapping("/{userGroupId}")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID userGroupId) {
        try {
            groupRepository.deleteById(userGroupId);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException(UserGroup.class, userGroupId.toString());
        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(UserGroup.class, userGroupId.toString());
        }
    }
}
