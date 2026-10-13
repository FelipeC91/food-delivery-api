package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.CityController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class CitiesLinkEngine {

    public static Link slashCities() {
        return WebMvcLinkBuilder.linkTo(CityController.class).withRel("/cities");
    }
    public static Link slashCitiesSlashCityId(Long cityId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).findOneResource(cityId)).withSelfRel();
    }
}
