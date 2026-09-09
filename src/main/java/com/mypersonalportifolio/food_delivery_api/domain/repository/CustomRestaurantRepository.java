package com.mypersonalportifolio.food_delivery_api.domain.repository;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface CustomRestaurantRepository {

    record ByNameLikeAndShippingCostBetweenFilterDTO(String name,
                                                           BigDecimal minimumShippingCost,
                                                            BigDecimal maximumShippingCost) {}

    List<Restaurant> customQueryByNameLikeAndShippingCostBetween(ByNameLikeAndShippingCostBetweenFilterDTO filterDTO);

    List<Restaurant> customQueryByFreeShippingAndNameLike(String name);
}
