package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.user;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.UserPasswordInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.UserOutputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/users", "/user"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserOutputDTO> listAllResources() {
        return userRepository.findAll()
                .stream()
                .map(UserOutputDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserOutputDTO> findOneResource(@PathVariable UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId.toString()));

        return ResponseEntity.ok(new UserOutputDTO(user));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDTO createResource(@RequestBody @Valid User userCandidate) {
        try {
            var savedUser = userService.registerNewUser(userCandidate);
            return new UserOutputDTO(savedUser);
        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(User.class, userCandidate.getEmail());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserOutputDTO> updateResource(@PathVariable UUID userId,
                                                        @RequestBody @Valid User userSource) {
        var userTarget = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId.toString()));

        var updatedUser = userService.updateProperties(userTarget, userSource);

        return ResponseEntity.ok(new UserOutputDTO(updatedUser));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<UserOutputDTO> updatePassword(@PathVariable UUID userId,
                                                        @RequestBody @Valid UserPasswordInputDTO passwordSource) {
        var userTarget = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId.toString()));

        var updatedUser = userService.updatePassword(userTarget, passwordSource);

        return ResponseEntity.ok(new UserOutputDTO(updatedUser));
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
