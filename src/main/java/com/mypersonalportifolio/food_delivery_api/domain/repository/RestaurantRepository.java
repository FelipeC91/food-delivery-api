package com.mypersonalportifolio.food_delivery_api.domain.repository;


import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.RestaurantBasicInfoDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID>,
                                                CustomRestaurantRepository,
                                                JpaSpecificationExecutor<Restaurant> {



    @Query("SELECT new com.mypersonalportifolio.food_delivery_api.domain.model.dto.RestaurantBasicInfoDTO(r.name, r.shippingCost, fc.name) " +
           "FROM Restaurant r JOIN r.foodCategory fc")
    List<RestaurantBasicInfoDTO> findAllReturningBasicInfo();

    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    Long countByFoodCategoryId(UUID foodCategory);
}
