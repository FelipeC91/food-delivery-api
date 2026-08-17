package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.query_specification;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant_;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantQueryPredicatesFactory {

    public static Specification<Restaurant> inFreeShippingCost() {
        return (root, query, builder) ->
                    builder.equal(root.get(Restaurant_.SHIPPING_COST), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> withSimilarName(String name) {
        return (root, query, builder) ->
                    builder.like(root.get(Restaurant_.NAME), "%" + name + "%");
    }

}
