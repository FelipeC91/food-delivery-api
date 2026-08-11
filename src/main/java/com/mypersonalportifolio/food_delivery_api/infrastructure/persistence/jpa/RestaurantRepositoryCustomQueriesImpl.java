package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant_;
import com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.specification.RestaurantQuerySpecifications;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Repository
public class RestaurantRepositoryCustomQueriesImpl //implements RestaurantRepositoryCustomQueries
{

//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    @Autowired @Lazy
//    private RestaurantRepository restaurantRepository;
//
//    @Override
//    public List<Restaurant> find(String name, BigDecimal shippingCostStartsIn, BigDecimal shippingCostEndsIn) {
//        var criteriaBuilder = entityManager.getCriteriaBuilder();
//        var criteria = criteriaBuilder.createQuery(Restaurant.class);
//        var root = criteria.from(Restaurant.class);
//
//        var predicates = new ArrayList<Predicate>();
//
//        if (!name.isBlank() && StringUtils.hasText(name))
//            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
//
//        if (shippingCostStartsIn != null) {
//            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Restaurant_.SHIPPING_COST), shippingCostStartsIn));
//        }
//
//        if (shippingCostEndsIn != null) {
//            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Restaurant_.SHIPPING_COST), shippingCostEndsIn));
//        }
//
//        criteria.where(predicates.toArray(new Predicate[0]));
//
//        var query = entityManager.createQuery(criteria);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Restaurant> findInFreeShipping(String name) {
//        return restaurantRepository.findAll(RestaurantQuerySpecifications.inFreeShippingCost()
//                                                                        .and(RestaurantQuerySpecifications.withSimilarName(name))
//                                            );
//    }
}
