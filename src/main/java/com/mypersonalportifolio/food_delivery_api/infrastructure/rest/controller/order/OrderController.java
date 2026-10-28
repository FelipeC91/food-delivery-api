package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order;

import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessNewOrderUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.filter.OrderSearchFilterDTO;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.query_specification.OrderQueryPredicatesFactory;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.OrderRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.OrderSimplifiedRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.pagination.PageableTranslator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ProcessNewOrderUseCase processNewOrderUseCase;
    private final OrderRepresentationModelAssembler assembler;
    private final OrderSimplifiedRepresentationModelAssembler simplifiedAssembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    public OrderController(OrderRepository orderRepository,
                           OrderService orderService, ProcessNewOrderUseCase processNewOrderUseCase,
                           OrderRepresentationModelAssembler assembler, OrderSimplifiedRepresentationModelAssembler simplifiedAssembler, PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.processNewOrderUseCase = processNewOrderUseCase;
        this.assembler = assembler;
        this.simplifiedAssembler = simplifiedAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PagedModel<OrderSimplifiedRepresentationModel> getAllResources(OrderSearchFilterDTO filter,
                                                                          @PageableDefault(size = 10) Pageable pageable) {
        var translatedPageable = translatePageable(pageable);

        var ordersPage = orderRepository.findAll( OrderQueryPredicatesFactory.buildOrderSpecification(filter), translatedPageable );

        return pagedResourcesAssembler.toModel(ordersPage, simplifiedAssembler);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{orderId}")
    public OrderRepresentationModel getResource(@PathVariable UUID orderId) {
        return assembler.toModel(orderService.findValidOrder(orderId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderRepresentationModel createResource(@RequestBody OrderInputDTO orderInputDTO) {
        return assembler.toModel( processNewOrderUseCase.execute(orderInputDTO) );

    }

    private Pageable translatePageable(Pageable pageableSource) {
        var sortingMap = Map.of("customer.name", "customer.name",
                                                    "customerName", "customer.name",
                                                    "restaurantName", "restaurant.name",
                                                    "restaurant.name", "restaurant.name",
                                                        "totalPrice", "totalPrice"
                                                            );
        return PageableTranslator.translate(pageableSource, sortingMap);
    }
}
