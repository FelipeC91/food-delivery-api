package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    public Order findValidOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                            .orElseThrow(() -> new EntityNotFoundException(Order.class, orderId.toString()));
    }

    @Autowired
    private RestaurantService restaurantService;

    public OrderOutputDTO acceptNewOrder(OrderInputDTO orderInputDTO) {
        var restaurantTarget = restaurantService.findValidrestaurant(orderInputDTO.restaurantId());

        var paymentMethodTarget = paymentMethodService.findValidPaymentMethod(orderInputDTO.paymentMethodId());




    }
}
