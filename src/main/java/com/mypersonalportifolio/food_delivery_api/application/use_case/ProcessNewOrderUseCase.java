package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.domain.exception.PaymentMethodNotAcceptableException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.PendingOrderAlreadyExistsException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.domain.service.PaymentMethodService;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.domain.service.UserService;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProcessNewOrderUseCase implements UseCase<OrderInputDTO, Order> {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;
    private final PaymentMethodService paymentMethodService;
    private final UserService userService;

    private final List<OrderStatus> NOT_PENDING_CONDITIONS = List.of( OrderStatus.ENTREGUE, OrderStatus.CANCELADO );

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
        var restaurantTarget = restaurantService.findValidRestaurant(orderCandidate.getRestaurantId());

        var paymentMethodTarget = paymentMethodService.findValidPaymentMethod(orderCandidate.getPaymentMethodId());

        var isAllowedPaymentMethod = restaurantTarget.getAllowedPaymentMethods().contains(paymentMethodTarget);

        if (!isAllowedPaymentMethod)
            throw new PaymentMethodNotAcceptableException(paymentMethodTarget.getDescription(), restaurantTarget.getName());

        var customer = userService.findVerifiedUser(orderCandidate.getCustomerId());

        if (orderRepository.existsByCustomerIdAndStatusNotIn(customer.getId(), NOT_PENDING_CONDITIONS))
            throw new PendingOrderAlreadyExistsException();

        var shippingAddress = orderService.desassembleAddress(orderCandidate.getShippingAddress());

        var orderItemSet = orderService.processItems(orderCandidate);


        var validOrder = Order.confirmNewOrder(restaurantTarget, paymentMethodTarget, customer, shippingAddress, orderItemSet);

        var order =   orderRepository.save(validOrder);

        log.info("Order has been saved successfully:\n {}",  order.toString());

        return order;
    }
}
