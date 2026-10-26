package com.mypersonalportifolio.food_delivery_api.application.use_case.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RestaurantInputDTO(
        @NotBlank
        String name,

        @Valid
        AddressInputDTO address,

        @PositiveOrZero
        @NotNull
        BigDecimal shippingCost,

        @NotNull
        FoodCategory foodCategory
) {


}
