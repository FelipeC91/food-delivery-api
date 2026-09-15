package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output;

import java.math.BigDecimal;
import java.util.UUID;

public record RestaurantBasicInfoOutputDTO(
        UUID id,
        String name,
        BigDecimal shippingCost
) {}
