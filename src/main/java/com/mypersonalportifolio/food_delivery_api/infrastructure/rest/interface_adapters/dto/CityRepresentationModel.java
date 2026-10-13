package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "city", collectionRelation = "cities")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CityRepresentationModel extends RepresentationModel<CityRepresentationModel> {

    private  Long id;

    private String name;

    @Setter
    private Long stateId;
}
