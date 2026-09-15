package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService userGroupService;

    @GetMapping
    public List<UserGroup> listAllResources() {
        return groupRepository.findAll();
    }

    @GetMapping("/{userGroupId}")
    public ResponseEntity<UserGroup> findOneResource(@PathVariable UUID userGroupId) {
        var userGroup = groupRepository.findById(userGroupId)
                .orElseThrow(() -> new EntityNotFoundException(UserGroup.class, userGroupId.toString()));

        return ResponseEntity.ok(userGroup);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserGroup createResource(@RequestBody @Valid UserGroup userGroupCandidate) {
        try {
            return groupRepository.save(userGroupCandidate);
        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(UserGroup.class, userGroupCandidate.getName());
        }
    }

    @PutMapping("/{userGroupId}")
    public ResponseEntity<UserGroup> updateResource(@PathVariable UUID userGroupId,
                                                     @RequestBody @Valid UserGroup userGroupSource) {
        var userGroupTarget = groupRepository.findById(userGroupId)
                .orElseThrow(() -> new EntityNotFoundException(UserGroup.class, userGroupId.toString()));

        var updatedUserGroup = userGroupService.updateProperties(userGroupTarget, userGroupSource);

        return ResponseEntity.ok(updatedUserGroup);
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
