package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderStatusInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateOrderStatusUseCaseTest {

    @Test
    void confirmsOrderAndSavesIt() {
        var orderService = mock(OrderService.class);
        var orderRepository = mock(OrderRepository.class);
        var useCase = new UpdateOrderStatusUseCase(orderService, orderRepository);
        var orderId = UUID.randomUUID();
        var order = mock(Order.class);
        when(orderService.findValidOrder(orderId)).thenReturn(order);

        var input = new OrderStatusInputDTO(OrderStatus.CONFIRMED);
        input.setOrderId(orderId);
        useCase.execute(input);

        verify(order).confirm();
        verify(orderRepository).save(order);
    }

    @Test
    void dispatchesOrderWithoutSavingIt() {
        var orderService = mock(OrderService.class);
        var orderRepository = mock(OrderRepository.class);
        var useCase = new UpdateOrderStatusUseCase(orderService, orderRepository);
        var orderId = UUID.randomUUID();
        var order = mock(Order.class);
        when(orderService.findValidOrder(orderId)).thenReturn(order);

        var input = new OrderStatusInputDTO(OrderStatus.READY_TO_DISPATCH);
        input.setOrderId(orderId);
        useCase.execute(input);

        verify(order).dispatch();
        verify(orderRepository, never()).save(order);
    }

    @Test
    void cancelsOrderAndSavesIt() {
        var orderService = mock(OrderService.class);
        var orderRepository = mock(OrderRepository.class);
        var useCase = new UpdateOrderStatusUseCase(orderService, orderRepository);
        var orderId = UUID.randomUUID();
        var order = mock(Order.class);
        when(orderService.findValidOrder(orderId)).thenReturn(order);

        var input = new OrderStatusInputDTO(OrderStatus.CANCELLED);
        input.setOrderId(orderId);
        useCase.execute(input);

        verify(order).cancel();
        verify(orderRepository).save(order);
    }

    @Test
    void leavesOrderUnchangedForStatusesWithoutAnAction() {
        var orderService = mock(OrderService.class);
        var orderRepository = mock(OrderRepository.class);
        var useCase = new UpdateOrderStatusUseCase(orderService, orderRepository);
        var orderId = UUID.randomUUID();
        var order = mock(Order.class);
        when(orderService.findValidOrder(orderId)).thenReturn(order);

        var input = new OrderStatusInputDTO(OrderStatus.PAID);
        input.setOrderId(orderId);
        useCase.execute(input);

        verify(order, never()).confirm();
        verify(order, never()).dispatch();
        verify(order, never()).cancel();
        verify(orderRepository, never()).save(order);
    }
}
