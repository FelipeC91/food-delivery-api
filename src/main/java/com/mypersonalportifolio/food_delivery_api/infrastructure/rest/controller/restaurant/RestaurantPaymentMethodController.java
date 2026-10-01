package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/restaurant/{restaurantId}/payment-methods")
@RestController
public class RestaurantPaymentMethodController {

    private final RestaurantService restaurantService;

    public RestaurantPaymentMethodController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<Set<PaymentMethod>> getResourceId(@PathVariable UUID restaurantId) {
        var restaurantTarget = restaurantService.findValidrestaurant(restaurantId);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(restaurantTarget.getAllowedPaymentMethods());
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
