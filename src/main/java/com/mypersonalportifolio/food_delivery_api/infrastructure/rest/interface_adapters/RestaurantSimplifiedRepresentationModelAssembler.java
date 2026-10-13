package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.FoodCategoryLinkEngine;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.RestaurantLinkEngine;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.mapstruct.RestaurantMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantSimplifiedRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantSimplifiedRepresentationModel> {

    private final RestaurantMapper restaurantMapper;

    public RestaurantSimplifiedRepresentationModelAssembler(RestaurantMapper restaurantMapper) {
        super(RestaurantController.class, RestaurantSimplifiedRepresentationModel.class);
        this.restaurantMapper = restaurantMapper;
    }


    @Override
    public RestaurantSimplifiedRepresentationModel toModel(Restaurant entity) {
        var representationModel = restaurantMapper.toSimplifiedRepresentationModel(entity);

        representationModel.add(RestaurantLinkEngine.slashRestaurants())
                .add(RestaurantLinkEngine.slashRestaurantsSlashRestaurantId(entity.getId()));

        representationModel.add(FoodCategoryLinkEngine.slashFoodCategorySlashFoodCategoryId(entity.getFoodCategory().getId()));

        return representationModel;
    }

    @Override
    public CollectionModel<RestaurantSimplifiedRepresentationModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(RestaurantLinkEngine.slashRestaurants());
    }
}
