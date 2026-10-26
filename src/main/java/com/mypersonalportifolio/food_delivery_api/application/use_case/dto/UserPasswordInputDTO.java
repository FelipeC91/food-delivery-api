package com.mypersonalportifolio.food_delivery_api.application.use_case.dto;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordInputDTO(
        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword
) {
}
