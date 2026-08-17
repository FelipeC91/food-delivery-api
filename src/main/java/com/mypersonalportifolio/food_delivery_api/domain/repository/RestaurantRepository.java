package com.mypersonalportifolio.food_delivery_api.domain.repository;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID>,
                                                CustomRestaurantRepository,
                                                JpaSpecificationExecutor<Restaurant> {
    //	@Query("from Restaurant where name like %:name% and foodCategory.id = :id")
    List<Restaurant> findByName(String nome, @Param("id") UUID foodCategory);

    //	List<Restaurant> findByNameContainingAndFoodCategoryId(String name, UUID foodCategory);

    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    Long countByFoodCategoryId(UUID foodCategory);
}
