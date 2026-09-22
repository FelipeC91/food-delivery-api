package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.query_specification;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order_;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant_;
import com.mypersonalportifolio.food_delivery_api.domain.model.User_;
import com.mypersonalportifolio.food_delivery_api.domain.repository.filter.OrderSearchFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OrderQueryPredicatesFactory {

    public static Specification<Order> buildOrderSpecification(OrderSearchFilterDTO filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filter.customerId() != null)
                predicates.add(criteriaBuilder.equal(root.get(Order_.CUSTOMER).get(User_.ID), filter.customerId()));

            if (filter.restaurantId() != null)
                predicates.add(criteriaBuilder.equal(root.get(Order_.RESTAURANT).get(Restaurant_.ID), filter.restaurantId()));

            if (filter.createdSince() != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.CREATED_AT), filter.createdSince()));

            if (filter.createdUntil() != null)
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Order_.DELIVERED_IN), filter.createdUntil()));

            return criteriaBuilder.and( predicates.toArray(new Predicate[predicates.size()]) );
        };
    }
}
