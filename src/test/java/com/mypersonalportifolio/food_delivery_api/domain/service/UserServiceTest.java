package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.UserPasswordInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.model.UserGroup;
import com.mypersonalportifolio.food_delivery_api.domain.repository.GroupRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldUpdatePasswordWhenOldPasswordMatches() {
        var user = new User();
        user.setPassword("old-password");
        var passwordInput = new UserPasswordInputDTO("old-password", "new-password");
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        var result = userService.updatePassword(user, passwordInput);

        assertSame(user, result);
        assertEquals("new-password", user.getPassword());
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    void shouldJoinUserToAnExistingGroup() {
        var groupId = UUID.randomUUID();
        var user = new User();
        var group = new UserGroup();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        userService.joinValidGroup(groupId, user);

        assertEquals(Set.of(group), user.getUserGroups());
        verify(userRepository).flush();
    }
}
