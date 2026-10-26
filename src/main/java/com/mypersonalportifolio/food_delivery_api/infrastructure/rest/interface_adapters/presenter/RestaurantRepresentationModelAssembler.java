package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.CitiesLinkEngine;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.FoodCategoryLinkEngine;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.RestaurantLinkEngine;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.RestaurantMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantRepresentationModel> {

    private final RestaurantMapper restaurantMapper;

    public RestaurantRepresentationModelAssembler(RestaurantMapper restaurantMapper) {
        super(RestaurantController.class, RestaurantRepresentationModel.class);
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public RestaurantRepresentationModel toModel(Restaurant entity) {
        var representationModel = restaurantMapper.toRepresentationModel(entity);

        representationModel.add(RestaurantLinkEngine.slashRestaurants())
                .add(RestaurantLinkEngine.slashRestaurantsSlashRestaurantId(entity.getId()));

        representationModel.getFoodCategory().add(FoodCategoryLinkEngine.slashFoodCategorySlashFoodCategoryId(entity.getFoodCategory().getId()));

        representationModel.getAddress().add(CitiesLinkEngine.slashCitiesSlashCityId(entity.getAddress().getCity().getId()));

        representationModel.add(RestaurantLinkEngine.slashRestaurantsSlashPaymentMethods(entity.getId()));

        representationModel.add(RestaurantLinkEngine.slashRestaurantsSlashManagers(entity.getId()));

        representationModel.add(RestaurantLinkEngine.slashRestaurantsSlashActivation(entity.getId()));

        representationModel.add(RestaurantLinkEngine.slashRestaurantsSlashOpening(entity.getId()));

        return representationModel;
    }

}
