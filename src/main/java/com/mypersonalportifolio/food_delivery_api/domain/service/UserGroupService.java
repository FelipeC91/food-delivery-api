package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Transactional
    public UserGroup updateProperties(UserGroup userGroupTarget, UserGroup userGroupSource) {
        userGroupTarget.setName(userGroupSource.getName());

        return userGroupRepository.saveAndFlush(userGroupTarget);
    }
}
