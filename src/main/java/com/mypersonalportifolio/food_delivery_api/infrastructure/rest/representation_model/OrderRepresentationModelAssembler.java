package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.CityController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.PaymentMethodController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.RestaurantProductController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order.OrderController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.user.UserController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.OrderRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.mapstruct.OrderMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class OrderRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderRepresentationModel> {

    private final OrderMapper orderMapper;

    public OrderRepresentationModelAssembler(OrderMapper orderMapper) {
        super(OrderController.class, OrderRepresentationModel.class);
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderRepresentationModel toModel(Order entity) {
        var representationModel = orderMapper.toRepresentationModel(entity);

        representationModel.add(WebMvcLinkBuilder.linkTo(OrderController.class).withRel("/orders"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getResource(entity.getId())).withSelfRel());

        representationModel.getRestaurant().add(WebMvcLinkBuilder.linkTo(RestaurantController.class).withRel("/restaurants"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class).findSingleResource(entity.getRestaurant().getId())).withSelfRel());

        representationModel.getPaymentMethod().add(WebMvcLinkBuilder.linkTo(PaymentMethodController.class).withRel("/payment-methods"))
                        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class).findSingleResource(entity.getPaymentMethod().getId())).withSelfRel());

        representationModel.getCustomer().add(WebMvcLinkBuilder.linkTo(UserController.class).withRel("/users"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findOneResource(entity.getCustomer().getId())).withSelfRel());

        representationModel.getShippingAddress().add(WebMvcLinkBuilder.linkTo(CityController.class).withRel("/cities"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).findOneResource(entity.getShippingAddress().getCity().getId())).withSelfRel());

        representationModel.getItems().forEach(item -> {
            item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantProductController.class)
                    .findOneResource(entity.getRestaurant().getId(), item.getProductId())).withRel("/products"));
        });

        return representationModel;
    }


}
