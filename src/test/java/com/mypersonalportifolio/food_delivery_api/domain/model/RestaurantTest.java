package com.mypersonalportifolio.food_delivery_api.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantTest {

    @Test
    void shouldBeActiveOnlyWhenEnabledWithAddressAndPaymentMethod() {
        var restaurant = restaurant();
        var paymentMethod = new PaymentMethod("Card");

        restaurant.setAddress(new Address("Downtown", "12345678", "Main Street", 1, null));
        assertFalse(restaurant.isActive());

        restaurant.attachAllowedPaymentMethod(paymentMethod);
        assertTrue(restaurant.isActive());

        restaurant.setActive(false);
        assertFalse(restaurant.isActive());
    }

    @Test
    void shouldPreventMutationThroughReadOnlyCollections() {
        var restaurant = restaurant();
        var paymentMethod = new PaymentMethod("Card");
        var manager = new User();
        restaurant.attachAllowedPaymentMethod(paymentMethod);
        restaurant.addManager(manager);

        assertThrows(UnsupportedOperationException.class,
                () -> restaurant.getAllowedPaymentMethods().add(new PaymentMethod("Cash")));
        assertThrows(UnsupportedOperationException.class,
                () -> restaurant.getManagers().remove(manager));
        assertThrows(UnsupportedOperationException.class,
                () -> restaurant.getProducts().clear());
    }

    @Test
    void shouldAddAndRemoveManagersAndAllowedPaymentMethods() {
        var restaurant = restaurant();
        var manager = new User();
        var paymentMethod = new PaymentMethod("Card");

        assertTrue(restaurant.addManager(manager));
        assertFalse(restaurant.addManager(manager));
        assertTrue(restaurant.removeManager(manager));
        assertFalse(restaurant.removeManager(manager));
        assertTrue(restaurant.attachAllowedPaymentMethod(paymentMethod));
        assertFalse(restaurant.attachAllowedPaymentMethod(paymentMethod));
        restaurant.detachAllowedPaymentMethod(paymentMethod);
        assertTrue(restaurant.getAllowedPaymentMethods().isEmpty());
    }

    private static Restaurant restaurant() {
        return new Restaurant("Test restaurant", BigDecimal.ZERO, null, null, new HashSet<>(), null, null,
                new HashSet<>(), true, false, new HashSet<>());
    }
}
