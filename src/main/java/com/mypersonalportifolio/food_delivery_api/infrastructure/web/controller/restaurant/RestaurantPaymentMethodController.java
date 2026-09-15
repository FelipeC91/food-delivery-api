package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/restaurant/{restaurantId}/payment-methods")
@RestController
public class RestaurantPaymentMethodController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<Set<PaymentMethod>> getResourceId(@PathVariable UUID restaurantId) {
        var restaurantTarget = restaurantRepository.findById(restaurantId)
                                                    .orElseThrow(()-> new EntityNotFoundException(Restaurant.class, restaurantId.toString()));

        return ResponseEntity.ok(restaurantTarget.getAllowedPaymentMethods());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{paymentMethodId}")
    public void createResource(@PathVariable UUID restaurantId,
                               @PathVariable UUID paymentMethodId) {
        restaurantService.addPaymentMethod(restaurantId, paymentMethodId);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{paymentMethodId}")
    public void deletePaymentMethod(@PathVariable UUID restaurantTargetId,
                                    @PathVariable UUID paymentMethodId) {
            restaurantService.handleDelete(restaurantTargetId, paymentMethodId);
    }
}
