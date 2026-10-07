package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model;

import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.CityController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.CityRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.mapstruct.CityMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CityRepresentationModelAssembler  extends RepresentationModelAssemblerSupport<City, CityRepresentationModel> {

    private final CityMapper cityMapper;

    public CityRepresentationModelAssembler(CityMapper  cityMapper) {
        super(CityController.class, CityRepresentationModel.class);
        this.cityMapper = cityMapper;
    }

    @Override
    public CityRepresentationModel toModel(City entity) {
        var representationModel = cityMapper.toRepresentationModel(entity);

        return assembleHateoasLinks(representationModel);
    }

    private CityRepresentationModel assembleHateoasLinks(CityRepresentationModel cityOutput) {
        cityOutput.add( WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).findOneResource(cityOutput.getId())).withSelfRel());
        cityOutput.add( WebMvcLinkBuilder.linkTo(CityController.class).withRel("/cities") );

        return cityOutput;
    }

    @Override
    public CollectionModel<CityRepresentationModel> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                .add( WebMvcLinkBuilder.linkTo(CityController.class).withSelfRel() );
    }
}
