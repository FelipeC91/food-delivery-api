package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderItem;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.OrderItemRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.OrderRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.input.OrderInputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer.name", target = "customerName")
    OrderSimplifiedRepresentationModel toSimplifiedRepresentationModel(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    OrderItemRepresentationModel toOrderItemRepresentationalModel(OrderItem orderItem);

    OrderRepresentationModel toRepresentationModel(Order order);




    Order toOrder(OrderInputDTO order);

}
