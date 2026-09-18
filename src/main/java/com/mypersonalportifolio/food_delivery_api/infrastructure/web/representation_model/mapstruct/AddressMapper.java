package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.AddressInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.AddressOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "city.state.name", target = "stateName")
    AddressOutputDTO toAddressOutputDTO(Address address);


    Address toAddress(AddressInputDTO addressInputDTO);


}
