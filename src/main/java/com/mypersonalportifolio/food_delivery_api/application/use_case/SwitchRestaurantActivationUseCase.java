package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantActivationStateInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.RestaurantCantBeClosedException;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SwitchRestaurantActivationUseCase implements UseCase<RestaurantActivationStateInputDTO, Void> {

    private final RestaurantService restaurantService;

    public SwitchRestaurantActivationUseCase(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Transactional
    @Override
    public Void execute(RestaurantActivationStateInputDTO restaurantActivationStateInput) {
        var restaurantTarget = restaurantService.findValidRestaurant(restaurantActivationStateInput.getRestaurantId());

        if (restaurantActivationStateInput.getActivation().equals(Boolean.TRUE)) {
            restaurantTarget.setActive(Boolean.TRUE);

        } else {
            if (restaurantService.isRestaurantIReadyToDeactivateOrClose(restaurantTarget))
                throw new RestaurantCantBeClosedException(restaurantTarget.getId());

            restaurantTarget.setActive(Boolean.FALSE);
        }

        log.info("{} activation has been switched to {}", restaurantActivationStateInput.getRestaurantId(), restaurantTarget.getOpen() ? "active" : "deactivated");

        return null;
    }
}
