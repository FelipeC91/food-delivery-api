package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.domain.exception.PaymentMethodNotAcceptableException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.domain.service.PaymentMethodService;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProcessNewOrderUseCase implements UseCase<OrderInputDTO, Order> {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;
    private final PaymentMethodService paymentMethodService;
    private final UserService userService;

    public ProcessNewOrderUseCase(OrderService orderService,
                                  OrderRepository orderRepository,
                                  RestaurantService restaurantService,
                                  PaymentMethodService paymentMethodService,
                                  UserService userService) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.restaurantService = restaurantService;
        this.paymentMethodService = paymentMethodService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Order execute(OrderInputDTO orderCandidate) {
        var restaurantTarget = restaurantService.findValidRestaurant(orderCandidate.restaurantId());

        var paymentMethodTarget = paymentMethodService.findValidPaymentMethod(orderCandidate.paymentMethodId());

        var isAllowedPaymentMethod = restaurantTarget.getAllowedPaymentMethods().contains(paymentMethodTarget);

        if (!isAllowedPaymentMethod)
            throw new PaymentMethodNotAcceptableException(paymentMethodTarget.getDescription(), restaurantTarget.getName());

        //TODO: catch user from user token
        var customer = userService.findVerifiedUser(UUID.fromString("55555555-6666-4777-8888-999999999999"));

        var shippingAddress = orderService.desassembleAddress(orderCandidate.shippingAddress());

        var orderItemSet = orderService.processItems(orderCandidate);


        var validOrder = Order.confirmNewOrder(restaurantTarget, paymentMethodTarget, customer, shippingAddress, orderItemSet);

        return   orderRepository.save(validOrder);
    }
}
