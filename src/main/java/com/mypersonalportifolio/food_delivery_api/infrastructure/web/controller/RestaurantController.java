package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> listAllResources() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> findResource(@PathVariable UUID restaurantId) {
        var restaurantTargetOptional = restaurantRepository.findById(restaurantId);

        if (restaurantTargetOptional.isPresent())
            return ResponseEntity.ok(restaurantTargetOptional.get());

        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<?> createResource(@RequestBody Restaurant restaurant) {
        try {
            var savedrestaurant = restaurantRepository.save(restaurant);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedrestaurant);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> updateResource(@PathVariable("restaurantId") UUID restaurantTargetId,
                                            @RequestBody Restaurant restaurantSource) {
        try {
            var restaurantTargetOptional = restaurantRepository.findById(restaurantTargetId);

            if (restaurantTargetOptional.isPresent()) {
                BeanUtils.copyProperties(restaurantSource, restaurantTargetOptional.get(), "id");

                var savedRestaurant = restaurantRepository.save(restaurantTargetOptional.get());

                return ResponseEntity.ok(savedRestaurant);

            }
            return ResponseEntity.notFound().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> updatePartiallyResource(@PathVariable("restaurantId") UUID restaurantTargetId,
                                                        @RequestBody Map<String, Objects> restaurantFieldsSourceProperties) {
        var restaurantTargetOptional = restaurantRepository.findById(restaurantTargetId);

        if (restaurantTargetOptional.isEmpty())
            return ResponseEntity.notFound().build();

        restaurantService.mergeProperties(restaurantFieldsSourceProperties, restaurantTargetOptional.get());

        return updateResource(restaurantTargetId, restaurantTargetOptional.get());
    }
}
