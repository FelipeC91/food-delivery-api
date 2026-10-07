package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order.OrderController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.mapstruct.OrderMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderSimplifiedRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderSimplifiedRepresentationModel> {

    private final OrderMapper orderMapper;

    public OrderSimplifiedRepresentationModelAssembler(OrderMapper orderMapper) {
        super(OrderController.class, OrderSimplifiedRepresentationModel.class);
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderSimplifiedRepresentationModel toModel(Order entity) {
        var representationModel =  orderMapper.toSimplifiedRepresentationModel(entity);

        representationModel.add(WebMvcLinkBuilder.linkTo(OrderController.class).withRel("/orders"))
                    .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getResource(entity.getId())).withSelfRel());

        return representationModel;
    }

    @Override
    public CollectionModel<OrderSimplifiedRepresentationModel> toCollectionModel(Iterable<? extends Order> entities) {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(OrderController.class).withRel("/orders"));
    }
}
