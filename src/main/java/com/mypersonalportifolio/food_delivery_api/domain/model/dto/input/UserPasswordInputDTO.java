package com.mypersonalportifolio.food_delivery_api.domain.model.dto.input;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordInputDTO(
        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword
) {
}
