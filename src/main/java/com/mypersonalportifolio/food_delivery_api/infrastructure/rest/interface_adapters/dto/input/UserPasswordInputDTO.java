package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.input;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordInputDTO(
        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword
) {
}
