package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.specification;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantQuerySpecifications {

    public static Specification<Restaurant> inFreeShippingCost() {
        return (root, query, builder) ->
                builder.equal(root.get("shippingCost"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> withSimilarName(String name) {
        return (root, query, builder) ->
                builder.like(root.get("name"), "%" + name + "%");
    }
}
