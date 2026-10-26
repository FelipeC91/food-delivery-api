package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PaymentMethodRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.PaymentMethodRepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/restaurants/{restaurantId}/payment-methods")
@RestController
public class RestaurantPaymentMethodController {

    private final RestaurantService restaurantService;
    private final PaymentMethodRepresentationModelAssembler assembler;

    public RestaurantPaymentMethodController(RestaurantService restaurantService, PaymentMethodRepresentationModelAssembler assembler) {
        this.restaurantService = restaurantService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PaymentMethodRepresentationModel>> getResourceId(@PathVariable UUID restaurantId) {
        var restaurantTarget = restaurantService.findValidRestaurant(restaurantId);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(assembler.toCollectionModel( restaurantTarget.getAllowedPaymentMethods(), restaurantId, null ));
    }

    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<Void> createResource(@PathVariable UUID restaurantId,
                               @PathVariable UUID paymentMethodId) {
        restaurantService.addPaymentMethod(restaurantId, paymentMethodId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable UUID restaurantTargetId,
                                    @PathVariable UUID paymentMethodId) {
            restaurantService.handleDelete(restaurantTargetId, paymentMethodId);

            return ResponseEntity.noContent().build();
    }
}
