package com.mypersonalportifolio.food_delivery_api.domain.event;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;


public record OrderConfirmedEvent(
    Order order
) {}

