package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.application.use_case.SwitchRestaurantActivationUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantActivationStateInputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/restaurants/{restaurantId}/activation")
@RestController
public class RestaurantActivationController {

    private final SwitchRestaurantActivationUseCase switchRestaurantActivationUseCase;

    public RestaurantActivationController(SwitchRestaurantActivationUseCase switchRestaurantActivationUseCase) {
        this.switchRestaurantActivationUseCase = switchRestaurantActivationUseCase;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activateResource(@PathVariable UUID restaurantId,
                                                 @RequestBody RestaurantActivationStateInputDTO restaurantActivationStateInput) {
        restaurantActivationStateInput.setRestaurantId(restaurantId);

        switchRestaurantActivationUseCase.execute(restaurantActivationStateInput);

        return ResponseEntity.noContent().build();
    }

}
