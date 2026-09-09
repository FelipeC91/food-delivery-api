package com.mypersonalportifolio.food_delivery_api.infrastructure.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.AddressOutputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.RestaurantInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.RestaurantOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);


    Restaurant dtoToRestaurant(RestaurantInputDTO restaurantInputDTO);

    @Mapping(source = "foodCategory.id", target = "foodCategoryId")
    RestaurantOutputDTO restaurantToOutputDTO(Restaurant restaurant);

    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "city.state.name", target = "stateName")
    AddressOutputDTO addressToAddressOutputDTO(Address address);

}
