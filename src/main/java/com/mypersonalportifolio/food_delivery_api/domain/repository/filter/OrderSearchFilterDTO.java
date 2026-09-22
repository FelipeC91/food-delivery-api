package com.mypersonalportifolio.food_delivery_api.domain.repository.filter;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderSearchFilterDTO(
        UUID customerId,
        UUID restaurantId,
        OffsetDateTime createdSince,
        OffsetDateTime createdUntil

) {
}
