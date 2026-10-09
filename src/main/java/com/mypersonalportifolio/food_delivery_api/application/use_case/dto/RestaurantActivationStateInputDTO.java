package com.mypersonalportifolio.food_delivery_api.application.use_case.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class RestaurantActivationStateInputDTO {

    @NotNull
    private final Boolean activation;

    @Setter
    private UUID restaurantId;
}
