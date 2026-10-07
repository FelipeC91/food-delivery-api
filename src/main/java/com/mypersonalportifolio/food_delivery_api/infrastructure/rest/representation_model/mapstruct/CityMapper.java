package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.CityRepresentationModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(source = "state.id", target = "stateId")
    CityRepresentationModel toRepresentationModel(City city);

}
