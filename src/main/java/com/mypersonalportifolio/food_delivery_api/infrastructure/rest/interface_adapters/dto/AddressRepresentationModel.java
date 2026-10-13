package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "address")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressRepresentationModel extends RepresentationModel<AddressRepresentationModel> {
    private String neighborhood;
    private String zipCode;
    private String streetName;
    private Integer streetNumber;
    private CityRepresentationModel city;
}
