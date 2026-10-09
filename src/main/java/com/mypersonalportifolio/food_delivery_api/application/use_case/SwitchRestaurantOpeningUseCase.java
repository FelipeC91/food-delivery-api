package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantOpeningStateInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.RestaurantCantBeClosedException;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SwitchRestaurantOpeningUseCase implements UseCase<RestaurantOpeningStateInputDTO, Void> {

    private final RestaurantService restaurantService;

    public SwitchRestaurantOpeningUseCase(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Transactional
    @Override
    public Void execute(RestaurantOpeningStateInputDTO restaurantOpeningStateInput) {
        var restaurantTarget = restaurantService.findValidRestaurant(restaurantOpeningStateInput.getRestaurantId());

        if (restaurantOpeningStateInput.getOpening().equals(Boolean.TRUE)) {
            restaurantTarget.setOpen(Boolean.TRUE);

        } else {
            if (restaurantService.isRestaurantIReadyToDeactivateOrClose(restaurantTarget))
                throw new RestaurantCantBeClosedException(restaurantTarget.getId());

            restaurantTarget.setOpen(Boolean.FALSE);
        }



        return null;
    }
}
