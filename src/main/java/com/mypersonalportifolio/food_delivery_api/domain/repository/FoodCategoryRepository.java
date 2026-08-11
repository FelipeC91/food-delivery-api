package com.mypersonalportifolio.food_delivery_api.domain.repository;


import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, UUID> {

    List<FoodCategory> queryByNameContaining(String name);


    Optional<FoodCategory> findByName(String name);

    boolean existsByName(String name);


}
