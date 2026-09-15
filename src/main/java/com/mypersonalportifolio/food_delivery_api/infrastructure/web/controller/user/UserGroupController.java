package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.user;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/groups")
public class UserGroupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public Set<UserGroup> getUserGroups(@PathVariable("userId") UUID userId) {
        var validUser = findValidUser(userId);

        return validUser.getUserGroups();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{groupId}")
    public void attachResource(@PathVariable("userId") UUID userId,
                               @PathVariable("groupId") UUID groupId) {
        var validUser = findValidUser(userId);

        userService.joinValidGroup(groupId, validUser);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void detachResource(@PathVariable("userId") UUID userId,
                               @PathVariable("groupId") UUID groupId) {
        var validUser = findValidUser(userId);

        userService.leaveGroup(validUser, groupId);
    }


    public User findValidUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId.toString()));
    }
}
