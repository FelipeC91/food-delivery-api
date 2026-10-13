package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class RestaurantLinkEngine {

    public static Link slashRestaurants() {
        return WebMvcLinkBuilder.linkTo(RestaurantController.class).withRel("/restaurants");
    }

    public static Link slashRestaurantsSlashRestaurantId(UUID restaurantId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantController.class).findSingleResource(restaurantId)).withSelfRel();
    }

    //PRODUCTS
    public static Link SlashProducts(UUID restaurantId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantProductController.class).listResources(restaurantId, null)).withSelfRel();

    }

    public static Link slashProductsSlashProductId(UUID restaurantId, UUID productId) {
            return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantProductController.class).findOneResource(restaurantId, productId)).withSelfRel();
    }

    public static Link slashProductsSlashProductIdSlashPhoto(UUID restaurantId, UUID productId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantProductController.class).findOneResource(restaurantId, productId)).withRel("/Foto");


    }

    //PAYMENT METHODS
    public static Link slashRestaurantsSlashPaymentMethods(UUID restaurantId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantPaymentMethodController.class).getResourceId(restaurantId)).withRel("/payment-methods");
    }

    public static Link slashRestaurantsSlashPaymentMethod(UUID restaurantId, UUID paymentMethodId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantPaymentMethodController.class).createResource(restaurantId, paymentMethodId)).withRel("/payment-method");
    }
    //MANAGERS

    public static Link slashRestaurantsSlashManagers(UUID restaurantId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantManagersController.class).getResourceId(restaurantId)).withRel("/managers");
    }
    //ACTIVATION
    public static Link slashRestaurantsSlashActivation(UUID restaurantId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantActivationController.class).activateResource(restaurantId, null)).withRel("/activation");
    }
    //OPENING

    public static Link slashRestaurantsSlashOpening(UUID restaurantId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestaurantOpeningController.class).activateResource(restaurantId, null)).withRel("/opening");
    }


}
