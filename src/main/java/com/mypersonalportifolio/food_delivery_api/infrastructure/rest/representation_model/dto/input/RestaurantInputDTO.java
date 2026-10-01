package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

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
