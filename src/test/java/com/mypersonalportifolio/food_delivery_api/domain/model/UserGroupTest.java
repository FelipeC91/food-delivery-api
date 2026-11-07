package com.mypersonalportifolio.food_delivery_api.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserGroupTest {

    @Test
    void shouldAttachAndDetachPermissions() {
        var group = new UserGroup();
        var permission = new Permission("READ_ORDERS", "Read orders");

        assertTrue(group.attachPermission(permission));
        assertFalse(group.attachPermission(permission));
        assertTrue(group.detachPermission(permission));
        assertFalse(group.detachPermission(permission));
        assertTrue(group.getPermissions().isEmpty());
    }

    @Test
    void shouldExposePermissionsAsReadOnly() {
        var group = new UserGroup();
        var permission = new Permission("READ_ORDERS", "Read orders");
        group.attachPermission(permission);

        assertThrows(UnsupportedOperationException.class,
                () -> group.getPermissions().clear());
    }
}
