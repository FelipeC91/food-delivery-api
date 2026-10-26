package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.AddressInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);


    Address toAddress(AddressInputDTO addressInputDTO);


}
