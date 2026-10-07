package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class StateRepresentationModel extends RepresentationModel<StateRepresentationModel> {
    private  Long id;

    private String name;

    public StateRepresentationModel(State state) {
        this.id = state.getId();
        this.name = state.getName();
    }
}