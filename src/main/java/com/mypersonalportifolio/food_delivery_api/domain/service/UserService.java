package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.input.UserPasswordInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
}
