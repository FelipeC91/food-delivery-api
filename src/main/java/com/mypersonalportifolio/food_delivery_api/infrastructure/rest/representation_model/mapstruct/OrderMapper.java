package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderItem;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderBasicInfoOutputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.OrderOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer.name", target = "customerName")
    OrderOutputDTO toOrderOutputDTO(Order order);

    @Mapping(source = "customer.name", target = "customerName")
    OrderBasicInfoOutputDTO toOrderBasicInfoOutputDTO(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    OrderOutputDTO.OrderItemOutputDTO toOrderItemOutputDTO(OrderItem orderItem);


    Order toOrder(OrderInputDTO order);

}
