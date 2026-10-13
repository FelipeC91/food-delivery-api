package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.ProductRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.ProductRepresentationModel;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

    private final ProductRepository productRepository;
    private final RestaurantService restaurantService;
    private final ProductService productService;
    private final ProductRepresentationModelAssembler assembler;

    public RestaurantProductController(ProductRepository productRepository,
                                       RestaurantService restaurantService,
                                       ProductService productService, ProductRepresentationModelAssembler assembler) {
        this.productRepository = productRepository;
        this.restaurantService = restaurantService;
        this.productService = productService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ProductRepresentationModel>> listResources(@PathVariable UUID restaurantId,
                                                                                     @RequestParam(value = "only-active", required = false) @Nullable Boolean onlyActive) {
        var productSet =  productService.retrieveRestaurantCatalog(restaurantId, (Objects.nonNull(onlyActive) && onlyActive));

        return ResponseEntity.ok(assembler.toCollectionModel(productSet));

    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductRepresentationModel> findOneResource(
            @PathVariable UUID restaurantId,
            @PathVariable UUID productId) {
        var product = assembler.toModel(productService.findValidProduct(productId));

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductRepresentationModel> createResource(@PathVariable UUID restaurantId, @Valid @RequestBody Product productCandidate) {
        var restaurant = restaurantService.findValidRestaurant(restaurantId);

        productCandidate.setRestaurant(restaurant);

        var product = productRepository.save(productCandidate);

        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(product));
    }

    @PutMapping
    public ResponseEntity<ProductRepresentationModel> associateResource(@PathVariable UUID restaurantId,
                                                   @RequestBody @Valid Product productCandidate) {
        var restaurant = restaurantService.findValidRestaurant(restaurantId);

        try {
            var product = productService.associateRestaurant(productCandidate, restaurant);

            return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assembler.toModel(product));

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(Product.class, productCandidate.getName());
        }
    }



}
