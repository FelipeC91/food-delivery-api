package com.mypersonalportifolio.food_delivery_api.infrastructure.persistence.jpa.query_projection;

import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesQueryProjection;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order_;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DailySalesQueryProjectionImpl implements DailySalesQueryProjection {

    private static final String DEFAULT_TIME_ZONE_OFFSET = "00:00";

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<DailySalesProjectionDTO> retrieveDailySales(FilterDTO filter) {

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(DailySalesProjectionDTO.class);
        var fromOrder = criteriaQuery.from(Order.class);

        var creationDate = processCreationDateExpression(criteriaBuilder, fromOrder, filter);
        var predicates = processPredicates(criteriaBuilder, fromOrder, filter);

        var selection = criteriaBuilder.construct(
                DailySalesProjectionDTO.class,
                creationDate,
                criteriaBuilder.count(fromOrder.get(Order_.ID)),
                criteriaBuilder.sum(fromOrder.get(Order_.TOTAL_PRICE))
        );

        criteriaQuery.select(selection)
                .where(predicates.toArray(new Predicate[0]))
                .groupBy(creationDate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private Expression<LocalDate> processCreationDateExpression(CriteriaBuilder criteriaBuilder, Root<Order> fromOrder, FilterDTO filter) {
        var convertedDateTime = criteriaBuilder.function(
                "CONVERT_TZ",
                LocalDate.class,
                fromOrder.get(Order_.CREATED_AT),
                criteriaBuilder.literal(DEFAULT_TIME_ZONE_OFFSET),
                criteriaBuilder.literal(filter.offsetTimeZone())
        );

        return criteriaBuilder.function("DATE", LocalDate.class, convertedDateTime);
    }

    private List<Predicate> processPredicates(CriteriaBuilder criteriaBuilder, Root<Order> fromOrder, FilterDTO filter) {
        var predicates = new ArrayList<Predicate>();

        predicates.add(fromOrder.get(Order_.STATUS).in(OrderStatus.CONFIRMADO, OrderStatus.ENTREGUE));

        if (filter.restaurantId() != null) {
            predicates.add(criteriaBuilder.equal(fromOrder.get(Order_.RESTAURANT).get(Restaurant_.ID), filter.restaurantId()));
        }

        if (filter.createdSince() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(fromOrder.get(Order_.CREATED_AT), filter.createdSince()));
        }

        if (filter.createdUntil() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(fromOrder.get(Order_.CREATED_AT), filter.createdUntil()));
        }

        return predicates;
    }
}
