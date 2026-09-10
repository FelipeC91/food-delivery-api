package com.mypersonalportifolio.food_delivery_api.infrastructure.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.output.AddressOutputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.input.RestaurantInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.output.RestaurantOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.crypto.spec.PSource;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);


    Restaurant dtoToRestaurant(RestaurantInputDTO restaurantInputDTO);


    City dtoToCity(RestaurantInputDTO.CityInputDTO cityInputDTO);

    @Mapping(source = "foodCategory.id", target = "foodCategoryId")
    RestaurantOutputDTO restaurantToOutputDTO(Restaurant restaurant);

    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "city.state.name", target = "stateName")
    AddressOutputDTO addressToAddressOutputDTO(Address address);



}
