package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.AddressInputDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.PaymentMethodNotAcceptableException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.PendingOrderAlreadyExistsException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderItem;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.domain.service.PaymentMethodService;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessNewOrderUseCaseTest {

    @Test
    void createsAndPersistsOrderForAnAllowedPaymentMethod() {
        var orderService = mock(OrderService.class);
        var orderRepository = mock(OrderRepository.class);
        var restaurantService = mock(RestaurantService.class);
        var paymentMethodService = mock(PaymentMethodService.class);
        var userService = mock(UserService.class);
        var useCase = new ProcessNewOrderUseCase(orderService, orderRepository, restaurantService, paymentMethodService, userService);

        var restaurantId = UUID.randomUUID();
        var paymentMethodId = UUID.randomUUID();
        var customerId = UUID.randomUUID();
        var candidate = new OrderInputDTO(restaurantId, paymentMethodId, mock(AddressInputDTO.class), List.of());
        candidate.setCustomerId(customerId);
        var restaurant = mock(Restaurant.class);
        var paymentMethod = mock(PaymentMethod.class);
        var customer = mock(User.class);
        var address = mock(Address.class);
        var items = Set.of(new OrderItem(null, 1, BigDecimal.TEN, BigDecimal.TEN, null));
        var savedOrder = mock(Order.class);

        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);
        when(paymentMethodService.findValidPaymentMethod(paymentMethodId)).thenReturn(paymentMethod);
        when(restaurant.getAllowedPaymentMethods()).thenReturn(Set.of(paymentMethod));
        when(userService.findVerifiedUser(customerId)).thenReturn(customer);
        when(customer.getId()).thenReturn(customerId);
        when(orderRepository.existsByCustomerIdAndStatusNotIn(any(), any())).thenReturn(false);
        when(orderService.desassembleAddress(candidate.getShippingAddress())).thenReturn(address);
        when(orderService.processItems(candidate)).thenReturn(items);
        when(restaurant.getShippingCost()).thenReturn(BigDecimal.ONE);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        var result = useCase.execute(candidate);

        assertSame(savedOrder, result);
        verify(orderRepository).save(any(Order.class));
        verify(orderService).desassembleAddress(candidate.getShippingAddress());
        verify(orderService).processItems(candidate);
    }

    @Test
    void rejectsPaymentMethodsNotAllowedByTheRestaurant() {
        var orderRepository = mock(OrderRepository.class);
        var restaurantService = mock(RestaurantService.class);
        var paymentMethodService = mock(PaymentMethodService.class);
        var useCase = new ProcessNewOrderUseCase(mock(OrderService.class), orderRepository, restaurantService,
                paymentMethodService, mock(UserService.class));
        var candidate = new OrderInputDTO(UUID.randomUUID(), UUID.randomUUID(), mock(AddressInputDTO.class), List.of());
        candidate.setCustomerId(UUID.randomUUID());
        var restaurant = mock(Restaurant.class);
        var paymentMethod = mock(PaymentMethod.class);
        when(restaurantService.findValidRestaurant(candidate.getRestaurantId())).thenReturn(restaurant);
        when(paymentMethodService.findValidPaymentMethod(candidate.getPaymentMethodId())).thenReturn(paymentMethod);
        when(restaurant.getAllowedPaymentMethods()).thenReturn(Set.of());
        when(paymentMethod.getDescription()).thenReturn("Cash");
        when(restaurant.getName()).thenReturn("Restaurant");

        assertThrows(PaymentMethodNotAcceptableException.class, () -> useCase.execute(candidate));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void rejectsNewOrderWhenCustomerHasAnotherPendingOrder() {
        var orderRepository = mock(OrderRepository.class);
        var restaurantService = mock(RestaurantService.class);
        var paymentMethodService = mock(PaymentMethodService.class);
        var userService = mock(UserService.class);
        var useCase = new ProcessNewOrderUseCase(mock(OrderService.class), orderRepository, restaurantService,
                paymentMethodService, userService);
        var customerId = UUID.randomUUID();
        var candidate = new OrderInputDTO(UUID.randomUUID(), UUID.randomUUID(), mock(AddressInputDTO.class), List.of());
        candidate.setCustomerId(customerId);
        var restaurant = mock(Restaurant.class);
        var paymentMethod = mock(PaymentMethod.class);
        var customer = mock(User.class);
        when(restaurantService.findValidRestaurant(candidate.getRestaurantId())).thenReturn(restaurant);
        when(paymentMethodService.findValidPaymentMethod(candidate.getPaymentMethodId())).thenReturn(paymentMethod);
        when(restaurant.getAllowedPaymentMethods()).thenReturn(Set.of(paymentMethod));
        when(userService.findVerifiedUser(customerId)).thenReturn(customer);
        when(customer.getId()).thenReturn(customerId);
        when(orderRepository.existsByCustomerIdAndStatusNotIn(any(), any())).thenReturn(true);

        assertThrows(PendingOrderAlreadyExistsException.class, () -> useCase.execute(candidate));

        verify(orderRepository, never()).save(any());
    }
}
