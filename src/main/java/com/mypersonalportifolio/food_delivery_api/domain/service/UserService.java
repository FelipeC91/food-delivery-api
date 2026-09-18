package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.UserPasswordInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public User updateProperties(User userTarget, User userSource) {
        if (!userSource.getEmail().matches(userTarget.getEmail())) {
            if (userRepository.existsByEmail(userSource.getEmail())) {
                throw new BusinessConstraintsViolationException("Email já associado a outro usuário");
            }
        }
        userTarget.setName(userSource.getName());
        userTarget.setEmail(userSource.getEmail());

        return userRepository.saveAndFlush(userTarget);
    }

    @Transactional
    public User updatePassword(User userTarget, UserPasswordInputDTO passwordSource) {
        if (!userTarget.getPassword().equals(passwordSource.oldPassword())) {
            throw new CandidateEntityInvalidException(User.class, "password");
        }

        userTarget.setPassword(passwordSource.newPassword());

        return userRepository.saveAndFlush(userTarget);
    }

    @Transactional
    public User registerNewUser(@Valid User userCandidate) {
        if (userRepository.existsByEmail( userCandidate.getEmail() ))
            throw new BusinessConstraintsViolationException("Email já associado a outro usuário");

        return userRepository.save(userCandidate);
    }

    @Transactional
    public void joinValidGroup(UUID groupId, User validUser) {
        var group = findValidGroup(groupId);

        validUser.joinGroup(group);

        userRepository.flush();
    }

    @Transactional
    public void leaveGroup(User validUser, UUID groupId) {
        var group = findValidGroup(groupId);

        validUser.leaveGroup(group);

        userRepository.flush();
    }

    private UserGroup findValidGroup(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(UserGroup.class, groupId.toString()));
    }

    public User findVerifiedUser(UUID userId) {
        return  userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId.toString()));

    }
}
