package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Set<Product>> listResources(@PathVariable UUID restaurantId, @RequestParam(value = "only-active", required = false) boolean onlyActive) {
        var restaurantTarget = findRestaurant(restaurantId);

        var productSet = restaurantTarget.getProductCatalog();

        if (onlyActive) {
            var activeSet = productSet.stream().filter(Product::isActive).collect(Collectors.toSet());
            return ResponseEntity.ok(activeSet);
        }
        return ResponseEntity.ok(productSet);

    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneResource(@PathVariable UUID restaurantId,
                                                   @PathVariable UUID productId) {

        var product = productRepository.findByIdAndRestaurantId(productId, restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, productId.toString()));

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createResource(@PathVariable UUID restaurantId, @Valid @RequestBody Product productCandate) {
        var restaurant = findRestaurant(restaurantId);

        productCandate.setRestaurant(restaurant);

        var product = productRepository.save(productCandate);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping
    public ResponseEntity<Product> associateResource(@PathVariable UUID restaurantId,
                                                   @RequestBody @Valid Product productCandidate) {
        var restaurant = findRestaurant(restaurantId);

        try {
            productService.associateRestaurant(productCandidate, restaurant);

            return ResponseEntity.status(HttpStatus.CREATED).body(productCandidate);
        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(Product.class, productCandidate.getName());
        }
    }



    private Restaurant findRestaurant(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(Restaurant.class, restaurantId.toString()));
    }


}
