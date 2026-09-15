package com.mypersonalportifolio.food_delivery_api.domain.repository;

import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByRestaurantId(UUID restaurantId);

    Optional<Product> findByIdAndRestaurantId(UUID productId, UUID restaurantId);
}
