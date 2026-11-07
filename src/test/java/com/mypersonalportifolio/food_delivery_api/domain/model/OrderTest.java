package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    void shouldCreateOrderUsingLineTotalsAndAddShippingCost() {
        var restaurant = restaurant(new BigDecimal("4.50"));
        var firstItem = item(new BigDecimal("3.00"), 2);
        var secondItem = item(new BigDecimal("7.25"), 1);

        var order = Order.createNewOrder(restaurant, null, null, null, Set.of(firstItem, secondItem));

        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(new BigDecimal("13.25"), order.getSubtotal());
        assertEquals(new BigDecimal("17.75"), order.getTotalPrice());
        assertEquals(new BigDecimal("4.50"), order.getShippingCost());
    }

    @Test
    void shouldConfirmPaidOrder() {
        var order = orderWithStatus(OrderStatus.PAID);

        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertNotNull(order.getConfirmedAt());
    }

    @Test
    void shouldCancelPaidOrder() {
        var order = orderWithStatus(OrderStatus.PAID);

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertNotNull(order.getCancelledAt());
    }

    @Test
    void shouldDispatchConfirmedOrderWithoutChangingConfirmationTimestamp() {
        var order = orderWithStatus(OrderStatus.CONFIRMED);
        ReflectionTestUtils.setField(order, "confirmedAt", java.time.OffsetDateTime.now());
        var confirmedAt = order.getConfirmedAt();

        order.dispatch();

        assertEquals(OrderStatus.READY_TO_DISPATCH, order.getStatus());
        assertEquals(confirmedAt, order.getConfirmedAt());
        assertNotNull(order.getDeliveredIn());
    }

    @Test
    void shouldRejectInvalidStatusTransitionWithBusinessException() {
        var order = orderWithStatus(OrderStatus.PAID);
        order.confirm();

        assertThrows(BusinessConstraintsViolationException.class, order::confirm);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    private static Order order() {
        return Order.createNewOrder(restaurant(new BigDecimal("2.00")), null, null, null, Set.of());
    }

    private static Order orderWithStatus(OrderStatus status) {
        var order = order();
        ReflectionTestUtils.setField(order, "status", status);
        return order;
    }

    private static Restaurant restaurant(BigDecimal shippingCost) {
        return new Restaurant("Test restaurant", shippingCost, null, null, Set.of(), null, null,
                Set.of(), true, false, Set.of());
    }

    private static OrderItem item(BigDecimal unitPrice, int quantity) {
        var product = new Product("Test product", "Description", unitPrice, true, null, null);
        return new OrderItem(product, quantity, unitPrice, unitPrice.multiply(BigDecimal.valueOf(quantity)), null);
    }
}
