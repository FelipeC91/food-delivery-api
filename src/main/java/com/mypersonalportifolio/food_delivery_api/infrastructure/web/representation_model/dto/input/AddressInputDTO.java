package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

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
) {

    public record CityInputDTO(
            String name
    ) {
    }
}