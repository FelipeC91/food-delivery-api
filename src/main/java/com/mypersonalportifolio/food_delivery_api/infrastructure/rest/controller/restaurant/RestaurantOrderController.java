package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.OrderSimplifiedRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller.RestaurantOrderControllerOpenApi;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.pagination.PageableTranslator;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RequestMapping("/restaurants/{restaurantId}/orders")
@RestController
public class RestaurantOrderController implements RestaurantOrderControllerOpenApi {

    private final OrderRepository orderRepository;
    private final OrderSimplifiedRepresentationModelAssembler assembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    public RestaurantOrderController(OrderRepository orderRepository,
                                     OrderSimplifiedRepresentationModelAssembler assembler,
                                     PagedResourcesAssembler<Order> pagedResourcesAssembler) {
        this.orderRepository = orderRepository;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public PagedModel<OrderSimplifiedRepresentationModel> getRestaurantOrders(
            @PathVariable UUID restaurantId,
            @PageableDefault(size = 10) Pageable pageable) {
        var translatedPageable = PageableTranslator.translate(pageable, Map.of(
                "customer.name", "customer.name",
                "customerName", "customer.name",
                "restaurantName", "restaurant.name",
                "restaurant.name", "restaurant.name",
                "totalPrice", "totalPrice"));

        var ordersPage = orderRepository.findByRestaurantId(restaurantId, translatedPageable);
        return pagedResourcesAssembler.toModel(ordersPage, assembler);
    }
}
