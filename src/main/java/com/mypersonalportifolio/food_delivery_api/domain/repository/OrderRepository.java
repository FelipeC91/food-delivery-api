package com.mypersonalportifolio.food_delivery_api.domain.repository;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {

    @Query("SELECT COUNT(o) > 0 FROM order_model AS o WHERE o.restaurant.id = :restaurantId AND o.status NOT IN (:safeCondition)")
    Boolean existsByRestaurantAndOrderStatusNotIn(UUID restaurantId, List<OrderStatus> safeCondition);
}
