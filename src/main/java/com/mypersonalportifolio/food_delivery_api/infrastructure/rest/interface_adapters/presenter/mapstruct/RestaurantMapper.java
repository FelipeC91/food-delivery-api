package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantSimplifiedRepresentationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CityMapper.class, UserMapper.class})
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant dtoToRestaurant(RestaurantInputDTO restaurantInputDTO);


    @Mapping(source = "foodCategory.name",target = "foodCategoryName")
    RestaurantSimplifiedRepresentationModel toSimplifiedRepresentationModel(Restaurant restaurant);

    RestaurantRepresentationModel toRepresentationModel(Restaurant restaurant);





}
