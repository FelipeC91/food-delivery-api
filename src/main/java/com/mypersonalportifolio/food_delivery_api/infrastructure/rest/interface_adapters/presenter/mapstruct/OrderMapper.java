package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderItem;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderItemRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.RestaurantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = RestaurantMapper.class)
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
