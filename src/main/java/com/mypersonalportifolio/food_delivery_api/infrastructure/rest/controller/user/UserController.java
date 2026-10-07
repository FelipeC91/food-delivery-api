package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.UserRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.input.UserPasswordInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.UserRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping({"/users", "/user"})
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserRepresentationModelAssembler assembler;

    public UserController(UserRepository userRepository,
                          UserService userService, UserRepresentationModelAssembler assembler) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<UserRepresentationModel> listAllResources() {
        return assembler.toCollectionModel(userRepository.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRepresentationModel> findOneResource(@PathVariable UUID userId) {
        var user = userService.findVerifiedUser(userId);

        return ResponseEntity.ok(assembler.toModel(user));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRepresentationModel createResource(@RequestBody @Valid User userCandidate) {
        try {
            var savedUser = userService.registerNewUser(userCandidate);
            return assembler.toModel(savedUser);

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(User.class, userCandidate.getEmail());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserRepresentationModel> updateResource(@PathVariable UUID userId,
                                                                  @RequestBody @Valid User userSource) {
        var userTarget = userService.findVerifiedUser(userId);

        var updatedUser = userService.updateProperties(userTarget, userSource);

        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<UserRepresentationModel> updatePassword(@PathVariable UUID userId,
                                                                  @RequestBody @Valid UserPasswordInputDTO passwordSource) {
        var userTarget = userService.findVerifiedUser(userId);

        var updatedUser = userService.updatePassword(userTarget, passwordSource);

        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID userId) {
        try {
            userRepository.deleteById(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException(User.class, userId.toString());
        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(User.class, userId.toString());
        }
    }
}
