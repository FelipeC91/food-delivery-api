package com.mypersonalportifolio.food_delivery_api.domain.model.dto.output;

import com.mypersonalportifolio.food_delivery_api.domain.model.User;

import java.time.OffsetDateTime;

public record UserOutputDTO(
        String name,
        String email,
        OffsetDateTime createdAt
) {
    public UserOutputDTO(User user) {
        this(user.getName(), user.getEmail(), user.getCreatedAt());
    }
}
