package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "state", collectionRelation = "states")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StateRepresentationModel extends RepresentationModel<StateRepresentationModel> {
    private  Long id;

    private String name;

    public StateRepresentationModel(State state) {
        this.id = state.getId();
        this.name = state.getName();
    }
}