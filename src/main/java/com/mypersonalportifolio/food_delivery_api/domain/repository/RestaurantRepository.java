package com.mypersonalportifolio.food_delivery_api.domain.repository;


import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

//    @EntityGraph(attributePaths = {"foodCategory", "paymentMethods"})
//    @Query("SELECT r FROM Restaurant AS r")
//    Page<Restaurant> findAllWithGraph(Pageable pageable);
//
//
//    default Page<RestaurantOutputDTO> findAllReturningBasicInfo(Pageable pageable) {
//         return findAllWithGraph(pageable)
//                .map(RestaurantOutputDTO::new);
//    }

    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    Long countByFoodCategoryId(UUID foodCategory);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Restaurant r JOIN r.managers m " +
            "WHERE r.id = :restaurantId AND m.id = :managerId")
    boolean existsManagerByRestaurantIdAndManagerId(@Param("restaurantId") UUID restaurantId,
                                                    @Param("managerId") UUID managerId);
}
