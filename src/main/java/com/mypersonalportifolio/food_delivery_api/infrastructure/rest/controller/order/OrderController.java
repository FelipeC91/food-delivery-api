package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order;

import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.filter.OrderSearchFilterDTO;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.query_specification.OrderQueryPredicatesFactory;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.pagination.PageableTranslator;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.pagination.PagerWrapper;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.OrderRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.OrderSimplifiedRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.OrderRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.input.OrderInputDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final OrderRepresentationModelAssembler assembler;
    private final OrderSimplifiedRepresentationModelAssembler simplifiedAssembler;

    public OrderController(OrderRepository orderRepository,
                           OrderService orderService,
                           OrderRepresentationModelAssembler assembler, OrderSimplifiedRepresentationModelAssembler simplifiedAssembler) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.assembler = assembler;
        this.simplifiedAssembler = simplifiedAssembler;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CollectionModel<OrderSimplifiedRepresentationModel> getAllResources(OrderSearchFilterDTO filter,
                                                                               @PageableDefault(size = 10) Pageable pageable) {
        var translatedPageable = translatePageable(pageable);

        var ordersPage = orderRepository.findAll( OrderQueryPredicatesFactory.buildOrderSpecification(filter), translatedPageable );

        ordersPage = new PagerWrapper<>(ordersPage.getContent(), pageable);

        return simplifiedAssembler.toCollectionModel(ordersPage);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{orderId}")
    public OrderRepresentationModel getResource(@PathVariable UUID orderId) {
        return assembler.toModel(orderService.findValidOrder(orderId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderRepresentationModel createResource(@RequestBody OrderInputDTO orderInputDTO) {
        return assembler.toModel( orderService.acceptNewOrder(orderInputDTO) );

    }

    private Pageable translatePageable(Pageable pageableSource) {
        var sortingMap = Map.of("customer.name", "customer.name",
                                                    "customerName", "customer.name",
                                                    "restaurant.name", "restaurant.name",
                                                        "totalPrice", "totalPrice"
                                                            );
        return PageableTranslator.translate(pageableSource, sortingMap);
    }
}
