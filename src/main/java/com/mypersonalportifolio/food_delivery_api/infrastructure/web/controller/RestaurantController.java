package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.dto.RestaurantBasicInfoDTO;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.ValidationGroups;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.UUID;


@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantBasicInfoDTO> listAllResources() {
        return restaurantRepository.findAllReturningBasicInfo();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> findResource(@PathVariable UUID restaurantId) {
        var restaurantTarget = restaurantRepository.findById(restaurantId)
                .orElseThrow( () -> new EntityNotFoundException(Restaurant.class, restaurantId.toString()));

        return ResponseEntity.ok(restaurantTarget);

    }

    @PostMapping
    private ResponseEntity<?> createResource(@RequestBody @Validated(ValidationGroups.Restaurant.class) Restaurant restaurantCandidate) {
        try {
            var savedRestaurant = restaurantRepository.save(restaurantCandidate);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(Restaurant.class, restaurantCandidate.getName());

        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> updateResource(@PathVariable("restaurantId") UUID restaurantTargetId,
                                            @RequestBody Restaurant restaurantSource) {
        var restaurantTarget = restaurantRepository.findById(restaurantTargetId)
                .orElseThrow( () -> new EntityNotFoundException(Restaurant.class, restaurantTargetId.toString()));

        var savedRestaurant = restaurantService.updateProperties(restaurantTarget, restaurantSource);

        return ResponseEntity.ok(savedRestaurant);
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> updatePartiallyResource(@PathVariable("restaurantId") UUID restaurantTargetId,
                                                        @RequestBody Map<String, Object> restaurantFieldsSourceProperties,
                                                        HttpInputMessage inputMessage) {
        var restaurantTarget = restaurantRepository.findById(restaurantTargetId)
                                                        .orElseThrow(()  -> new EntityNotFoundException(Restaurant.class, restaurantTargetId.toString() ));

        try {
            restaurantService.mergeProperties(restaurantFieldsSourceProperties, restaurantTarget);

        } catch (IllegalArgumentException e) {
        var rootCause = ExceptionUtils.getRootCause(e);

        throw new HttpMessageNotReadableException(e.getMessage(), rootCause, inputMessage);
    }


        return updateResource(restaurantTargetId, restaurantTarget);
    }
}
