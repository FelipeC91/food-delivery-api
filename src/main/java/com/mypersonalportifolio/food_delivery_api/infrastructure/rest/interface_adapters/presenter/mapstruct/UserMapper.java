package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct;


import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserRepresentationModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRepresentationModel toRepresentationModel(User user);
}
