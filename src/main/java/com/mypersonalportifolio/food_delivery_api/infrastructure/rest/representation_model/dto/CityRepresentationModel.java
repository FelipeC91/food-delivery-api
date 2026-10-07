package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class CityRepresentationModel extends RepresentationModel<CityRepresentationModel> {

    private  Long id;

    private String name;

    @Setter
    private Long stateId;
}
