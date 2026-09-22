package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.order;

import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.filter.OrderSearchFilterDTO;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.query_specification.OrderQueryPredicatesFactory;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.PageableTranslator;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderBasicInfoOutputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderOutputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

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
    public Page<OrderBasicInfoOutputDTO> getAllResources(OrderSearchFilterDTO filter, @PageableDefault(size = 10) Pageable pageable) {
        var translatedPageable = translatePageable(pageable);


        return orderRepository.findAll( OrderQueryPredicatesFactory.buildOrderSpecification(filter), translatedPageable )
                                        .map(orderMapper::toOrderBasicInfoOutputDTO);
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

    private Pageable translatePageable(Pageable pageableSource) {
        var sortingMap = Map.of("nomecliente", "customer.name",
                                                    "nomeRestaurante", "restaurant.name",
                                                        "customerName", "customer.name",
                                                        "totalPrice", "totalPrice"
                                                            );
        return PageableTranslator.translate(pageableSource, sortingMap);
    }
}
