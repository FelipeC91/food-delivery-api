package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Set<Product>> listResources(@PathVariable UUID restaurantId,
                                                      @RequestParam(value = "only-active", required = false) boolean onlyActive) {
        var productSet =  productService.retrieveRestaurantCatalog(restaurantId, onlyActive);

        return ResponseEntity.ok(productSet);

    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneResource(@PathVariable UUID productId) {
        var product = productService.findValidProduct(productId);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createResource(@PathVariable UUID restaurantId, @Valid @RequestBody Product productCandidate) {
        var restaurant = restaurantService.findValidrestaurant(restaurantId);

        productCandidate.setRestaurant(restaurant);

        var product = productRepository.save(productCandidate);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping
    public ResponseEntity<Product> associateResource(@PathVariable UUID restaurantId,
                                                   @RequestBody @Valid Product productCandidate) {
        var restaurant = restaurantService.findValidrestaurant(restaurantId);

        try {
            var product = productService.associateRestaurant(productCandidate, restaurant);

            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(Product.class, productCandidate.getName());
        }
    }



}
