package com.mypersonalportifolio.food_delivery_api.domain.model;


import lombok.Getter;

@Getter
public enum OrderStatus {

    CREATED,
    PAID,
    CONFIRMED,
    READY_TO_DISPATCH,
    ON_THE_WAY,
    DELIVERED,
    CANCELLED;
}
