package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output;

import com.mypersonalportifolio.food_delivery_api.domain.model.Address;

public record AddressOutputDTO(
        String neighborhood,
        String zipCode,
        String streetName,
        Integer streetNumber,
        String cityName,
        String stateName

) {

    public AddressOutputDTO(Address a) {
        this(a.getNeighborhood(),
                a.getZipCode(),
                a.getStreetName(),
                a.getStreetNumber(),
                a.getCity().getName(),
                a.getCity().getState().getName()

        );
    }
}
