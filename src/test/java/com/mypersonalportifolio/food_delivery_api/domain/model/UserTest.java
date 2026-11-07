package com.mypersonalportifolio.food_delivery_api.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void shouldJoinAndLeaveGroups() {
        var user = new User();
        var group = new UserGroup();

        assertTrue(user.joinGroup(group));
        assertFalse(user.joinGroup(group));
        assertTrue(user.leaveGroup(group));
        assertFalse(user.leaveGroup(group));
        assertTrue(user.getUserGroups().isEmpty());
    }

    @Test
    void shouldExposeGroupsAsReadOnly() {
        var user = new User();
        var group = new UserGroup();
        user.joinGroup(group);

        assertThrows(UnsupportedOperationException.class,
                () -> user.getUserGroups().clear());
    }
}
