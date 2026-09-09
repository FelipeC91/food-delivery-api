package com.mypersonalportifolio.food_delivery_api.domain.repository;


import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.RestaurantOutputDTO;

import org.springframework.data.jpa.repository.EntityGraph;
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



//    @Query("""
//             SELECT new com.mypersonalportifolio.food_delivery_api.domain.model.dto.RestaurantBasicInfoDTO(r.name, r.shippingCost, fc.name, pm)
//                            FROM Restaurant AS r
//                            JOIN r.foodCategory AS fc
//                            LEFT JOIN r.paymentMethods pm
//            """)
//    List<RestaurantBasicInfoDTO> findAllReturningBasicInfo();

    @EntityGraph(attributePaths = {"foodCategory", "paymentMethods"})
    @Query("SELECT r FROM Restaurant AS r")
    List<Restaurant> findAllWithGraph();


    default List<RestaurantOutputDTO> findAllReturningBasicInfo() {
        return findAllWithGraph().stream()
                .map(RestaurantOutputDTO::new)
                .toList();
    }

    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    Long countByFoodCategoryId(UUID foodCategory);
}
