package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.AddressInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderItem;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.AddressMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CityService  cityService;
    private final AddressMapper addressMapper;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        CityService cityService,
                        AddressMapper addressMapper,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.cityService = cityService;
        this.addressMapper = addressMapper;
        this.productService = productService;
    }

    public Set<OrderItem> processItems(OrderInputDTO orderCandidate) {
        return orderCandidate.items().stream().map( i -> {
            var product = productService.findValidProduct(i.productId());
            var totalPrice = product.getPrice().multiply(BigDecimal.valueOf(i.quantity()));

            return new OrderItem(product, i.quantity(), product.getPrice(), totalPrice, i.note());
        }).collect(Collectors.toSet());
    }

    public Address desassembleAddress(AddressInputDTO addressInputDTO) {
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
