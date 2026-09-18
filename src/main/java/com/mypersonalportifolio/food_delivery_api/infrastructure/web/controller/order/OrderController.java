package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct.OrderMapper;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderBasicInfoOutputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/orders")
@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrderBasicInfoOutputDTO> getAllResources() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderBasicInfoOutputDTO).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{orderId}")
    public OrderOutputDTO getResource(@PathVariable UUID orderId) {
        return orderMapper.toOrderOutputDTO(orderService.findValidOrder(orderId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderOutputDTO createResource(@RequestBody OrderInputDTO orderInputDTO) {
        return orderService.acceptNewOrder(orderInputDTO);
    }
}
