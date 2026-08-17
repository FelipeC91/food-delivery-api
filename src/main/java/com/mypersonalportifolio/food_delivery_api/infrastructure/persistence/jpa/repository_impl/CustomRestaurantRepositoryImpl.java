package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.repository_impl;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant_;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CustomRestaurantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomRestaurantRepositoryImpl implements CustomRestaurantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurant> customFindByNameLikeAndShippingCostBetween(ByNameLikeAndShippingCostBetweenFilterDTO filterDTO){

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        var fromRestaurant = criteriaQuery.from(Restaurant.class);


        var predicates = setUpCriteriaPredicates(criteriaBuilder, fromRestaurant, filterDTO);

        criteriaQuery.where(predicates);

        return entityManager.createQuery(criteriaQuery)
                                .getResultList();
    }

    private Predicate[] setUpCriteriaPredicates(CriteriaBuilder criteriaBuilder, Root<Restaurant> fromRestaurant, ByNameLikeAndShippingCostBetweenFilterDTO filterDTO) {
        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText( filterDTO.name() ) && !filterDTO.name().isBlank())
            predicates.add(criteriaBuilder.like(fromRestaurant.get(Restaurant_.NAME), "%" + filterDTO.name() + "%"));

        if (filterDTO.minimumShippingCost() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(fromRestaurant.get(Restaurant_.SHIPPING_COST), filterDTO.minimumShippingCost()));
        }

        if (filterDTO.maximumShippingCost() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(fromRestaurant.get(Restaurant_.SHIPPING_COST), filterDTO.maximumShippingCost()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
//
//
//    @Autowired @Lazy
//    private RestaurantRepository restaurantRepository;
//
//
//    @Override
//    public List<Restaurant> findInFreeShipping(String name) {
//        return restaurantRepository.findAll(RestaurantQuerySpecifications.inFreeShippingCost()
//                                                                        .and(RestaurantQuerySpecifications.withSimilarName(name))
//                                            );
//    }
}
