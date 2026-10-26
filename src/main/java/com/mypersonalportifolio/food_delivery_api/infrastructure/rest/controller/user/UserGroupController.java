package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserGroupRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.UserGroupRepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/groups")
public class UserGroupController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserGroupRepresentationModelAssembler assembler;

    public UserGroupController(UserRepository userRepository,
                               UserService userService, UserGroupRepresentationModelAssembler assembler) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<UserGroupRepresentationModel> getUserGroups(@PathVariable("userId") UUID userId) {
        var validUser = findValidUser(userId);

        return assembler.toCollectionModel(validUser.getUserGroups());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{groupId}")
    public ResponseEntity<Void> attachResource(@PathVariable("userId") UUID userId,
                               @PathVariable("groupId") UUID groupId) {
        var validUser = findValidUser(userId);

        userService.joinValidGroup(groupId, validUser);

        return ResponseEntity.noContent().build();

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public ResponseEntity<Void> detachResource(@PathVariable("userId") UUID userId,
                                         @PathVariable("groupId") UUID groupId) {
        var validUser = findValidUser(userId);

        userService.leaveGroup(validUser, groupId);

        return ResponseEntity.noContent().build();
    }


    public User findValidUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId.toString()));
    }
}
