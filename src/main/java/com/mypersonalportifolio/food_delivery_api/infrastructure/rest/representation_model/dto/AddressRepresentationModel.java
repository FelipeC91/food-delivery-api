package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class AddressRepresentationModel extends RepresentationModel<AddressRepresentationModel> {
    private String neighborhood;
    private String zipCode;
    private String streetName;
    private Integer streetNumber;
    private CityRepresentationModel city;
}
