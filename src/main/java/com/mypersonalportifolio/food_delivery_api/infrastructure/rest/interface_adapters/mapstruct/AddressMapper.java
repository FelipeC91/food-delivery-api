package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.input.AddressInputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);


    Address toAddress(AddressInputDTO addressInputDTO);


}
