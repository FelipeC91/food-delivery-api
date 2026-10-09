package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantActivationStateInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.RestaurantCantBeClosedException;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            restaurantTarget.setOpen(Boolean.TRUE);

        } else {
            if (restaurantService.isRestaurantIReadyToDeactivateOrClose(restaurantTarget))
                throw new RestaurantCantBeClosedException(restaurantTarget.getId());

            restaurantTarget.setOpen(Boolean.FALSE);
        }

        return null;
    }
}
