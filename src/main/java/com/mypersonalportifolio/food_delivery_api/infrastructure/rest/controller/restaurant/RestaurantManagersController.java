package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.UserRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.UserRepresentationModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("restaurants/{restaurantId}/managers")
@RestController
public class RestaurantManagersController {

    private final RestaurantService restaurantService;
    private final UserRepresentationModelAssembler userAssembler;

    public RestaurantManagersController(RestaurantService restaurantService,
                                        UserRepresentationModelAssembler userAssembler) {
        this.restaurantService = restaurantService;
        this.userAssembler = userAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<UserRepresentationModel>> getResourceId(@PathVariable UUID restaurantId) {
        var restaurantTarget = restaurantService.findValidRestaurant(restaurantId);

        var managers = userAssembler.toCollectionModel(restaurantTarget.getManagers(), restaurantId);

        return ResponseEntity.ok(managers);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void attachResource(@PathVariable UUID restaurantId, @PathVariable UUID userId) {
        var restaurantTarget = restaurantService.findValidRestaurant(restaurantId);

        restaurantService.addManager(restaurantTarget, userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{paymentMethodId}")
    public void detachResource(@PathVariable UUID restaurantTargetId, @PathVariable UUID paymentMethodId) {
        var validRestaurant = restaurantService.findValidRestaurant(restaurantTargetId);

        restaurantService.removeManager(validRestaurant, paymentMethodId);

    }

}
