package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.PaymentMethodNotAcceptableException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderItem;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.AddressInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderOutputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct.AddressMapper;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService  cityService;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductService productService;

    @Transactional
    public OrderOutputDTO acceptNewOrder(OrderInputDTO orderCandidate) {
        var restaurantTarget = restaurantService.findValidrestaurant(orderCandidate.restaurantId());

        var paymentMethodTarget = paymentMethodService.findValidPaymentMethod(orderCandidate.paymentMethodId());

        var isAllowedPaymentMethod = restaurantTarget.getAllowedPaymentMethods().contains(paymentMethodTarget);

        if (!isAllowedPaymentMethod)
            throw new PaymentMethodNotAcceptableException(paymentMethodTarget.getDescription(), restaurantTarget.getName());

        //TODO: catch user from user token
        var customer = userService.findVerifiedUser(UUID.fromString("55555555-6666-4777-8888-999999999999"));

        var shippingAddress = assembleAddress(orderCandidate.shippingAddress());

        var orderItemSet = processItems(orderCandidate);



        var validOrder = Order.confirmNewOrder(restaurantTarget, paymentMethodTarget, customer, shippingAddress, orderItemSet);

        validOrder =  orderRepository.save(validOrder);

        return orderMapper.toOrderOutputDTO(validOrder);
    }


    private Set<OrderItem> processItems(OrderInputDTO orderCandidate) {
        return orderCandidate.items().stream().map( i -> {
            var product = productService.findValidProduct(i.productId());
            var totalPrice = product.getPrice().multiply(BigDecimal.valueOf(i.quantity()));

            return new OrderItem(product, i.quantity(), product.getPrice(), totalPrice, i.note());
        }).collect(Collectors.toSet());
    }

    private Address assembleAddress(AddressInputDTO addressInputDTO) {
        var address = addressMapper.toAddress(addressInputDTO);

        var validCity = cityService.findValidCityByName(address.getCity().getName());

        address.setCity(validCity);

        return address;
    }


    public Order findValidOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(Order.class, orderId.toString()));
    }
}
