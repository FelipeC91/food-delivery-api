package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.UserRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.UserRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RequestMapping("restaurant/{restaurantId}/managers")
@RestController
public class RestaurantManagersController {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantService restaurantService;

    private final UserRepresentationModelAssembler userAssembler;

    public RestaurantManagersController(RestaurantRepository restaurantRepository, RestaurantService restaurantService, UserRepresentationModelAssembler userAssembler) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
        this.userAssembler = userAssembler;
    }


    @GetMapping
    public ResponseEntity<Set<UserRepresentationModel>> getResourceId(@PathVariable UUID restaurantId) {
        var restaurantTarget = findValidRestaurant(restaurantId);

        var managers = restaurantTarget.getManagers().stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(managers);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void attachResource(@PathVariable UUID restaurantId, @PathVariable UUID userId) {
        var restaurantTarget = findValidRestaurant(restaurantId);

        restaurantService.addManager(restaurantTarget, userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{paymentMethodId}")
    public void detachResource(@PathVariable UUID restaurantTargetId, @PathVariable UUID paymentMethodId) {
        var validRestaurant = findValidRestaurant(restaurantTargetId);

        restaurantService.removeManager(validRestaurant, paymentMethodId);

    }

    private Restaurant findValidRestaurant(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                                    .orElseThrow(()-> new EntityNotFoundException(Restaurant.class, restaurantId.toString()));
    }
}
