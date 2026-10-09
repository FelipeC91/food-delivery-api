package com.mypersonalportifolio.food_delivery_api.application.use_case.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class RestaurantOpeningStateInputDTO {

    @NotNull
    private final Boolean opening;

    @Setter
    private UUID restaurantId;
}
