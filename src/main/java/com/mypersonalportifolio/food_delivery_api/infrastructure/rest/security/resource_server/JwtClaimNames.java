package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum JwtClaimNames {
    USER_ID("user_id"),
    USER_NAME("user_name"),
    AUTHORITIES("authorities");

    @Getter
    private final String key;


}
