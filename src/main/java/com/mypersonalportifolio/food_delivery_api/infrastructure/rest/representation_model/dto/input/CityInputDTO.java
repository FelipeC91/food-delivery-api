package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public record CityInputDTO(
        @Schema(example = "Indaiatuba")
        @NotBlank
        String name,

        @Schema(example = "1", description = "valid state is in 1~26 range")
        @Range(min = 1L, max = 26L)
        Long stateId
) {}
