package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.OrderItem;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.OrderInputDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);



    OrderItem toOrderItem(OrderInputDTO.OrderItemInputDTO orderItemInputDTO);

}
