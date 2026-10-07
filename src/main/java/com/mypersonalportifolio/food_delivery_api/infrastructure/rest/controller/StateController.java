package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;


import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import com.mypersonalportifolio.food_delivery_api.domain.repository.StateRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.StateRepresentationModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/{states}")
@RestController
public class StateController {
    private final StateRepository stateRepository;

    public StateController(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @GetMapping
    public CollectionModel<StateRepresentationModel> getResources() {
        return CollectionModel.of(stateRepository.findAll().stream().map(StateRepresentationModel::new).toList());
    }

    @GetMapping("/{stateId}")
    public StateRepresentationModel getSingleResource(Long stateId) {
        var state = stateRepository.findById(stateId)
                                        .orElseThrow( () -> new EntityNotFoundException(State.class, stateId.toString()));


        return new StateRepresentationModel(state)
                        .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StateController.class).getSingleResource(stateId)).withSelfRel())
                        .add(WebMvcLinkBuilder.linkTo(this.getClass()).withRel("/states"));
    }
}