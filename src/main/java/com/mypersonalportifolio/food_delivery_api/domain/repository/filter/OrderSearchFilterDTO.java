package com.mypersonalportifolio.food_delivery_api.domain.repository.filter;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderSearchFilterDTO(
        UUID customerId,
        UUID restaurantId,

        @Schema(example = "2019-12-02T23:59:59Z")
        OffsetDateTime createdSince,
        @Schema(example = "2026-11-29T23:59:00")
        OffsetDateTime createdUntil

) {
}
