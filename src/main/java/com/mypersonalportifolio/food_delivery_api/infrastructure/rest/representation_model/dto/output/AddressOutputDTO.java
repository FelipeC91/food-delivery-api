package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.output;

import com.mypersonalportifolio.food_delivery_api.domain.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

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
