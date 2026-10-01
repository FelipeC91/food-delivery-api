package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.RestaurantBasicInfoOutputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.RestaurantInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.RestaurantOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant dtoToRestaurant(RestaurantInputDTO restaurantInputDTO);


    @Mapping(source = "foodCategory.id", target = "foodCategoryId")
    RestaurantOutputDTO restaurantToOutputDTO(Restaurant restaurant);


    RestaurantBasicInfoOutputDTO restaurantToBasicInfoDTO(Restaurant restaurant);





}
