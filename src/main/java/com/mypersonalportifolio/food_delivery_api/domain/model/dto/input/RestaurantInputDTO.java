package com.mypersonalportifolio.food_delivery_api.domain.model.dto.input;

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
        public record AddressInputDTO(
                @NotBlank
                String neighborhood,

                @Pattern(regexp = "^[0-9]{8}$")
                String zipCode,

                @NotBlank
                String streetName,

                @Positive
                Integer streetNumber,

                @NotNull
                CityInputDTO city
        ) {}

        public record CityInputDTO(

                String name
        ){}

}
