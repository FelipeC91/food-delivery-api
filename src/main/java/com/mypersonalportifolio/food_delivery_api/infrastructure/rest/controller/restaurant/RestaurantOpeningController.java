package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;


import com.mypersonalportifolio.food_delivery_api.application.use_case.SwitchRestaurantOpeningUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantOpeningStateInputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/restaurants/{restaurantId}/opening")
@RestController
public class RestaurantOpeningController {

    private final SwitchRestaurantOpeningUseCase switchRestaurantOpeningUseCase;

    public RestaurantOpeningController(SwitchRestaurantOpeningUseCase switchRestaurantOpeningUseCase) {
        this.switchRestaurantOpeningUseCase = switchRestaurantOpeningUseCase;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activateResource(@PathVariable UUID restaurantId,
                                                 @RequestBody RestaurantOpeningStateInputDTO  restaurantOpeningStateInput) {
        restaurantOpeningStateInput.setRestaurantId(restaurantId);

        switchRestaurantOpeningUseCase.execute(restaurantOpeningStateInput);

        return ResponseEntity.noContent().build();
    }
}
