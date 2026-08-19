package com.mypersonalportifolio.food_delivery_api.domain.model.dto;

import java.math.BigDecimal;

public record RestaurantBasicInfoDTO(String name, BigDecimal shippingCost, String foodCategoryName) {
}
