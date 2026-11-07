package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldFilterInactiveProductsFromRestaurantCatalog() {
        var restaurantId = UUID.randomUUID();
        var activeProduct = new Product(UUID.randomUUID());
        activeProduct.setActive(true);
        var inactiveProduct = new Product(UUID.randomUUID());
        inactiveProduct.setActive(false);
        var restaurant = new Restaurant();
        restaurant.setProducts(new HashSet<>(Set.of(activeProduct, inactiveProduct)));
        when(restaurantService.findValidRestaurant(restaurantId)).thenReturn(restaurant);

        var result = productService.retrieveRestaurantCatalog(restaurantId, true);

        assertEquals(Set.of(activeProduct), result);
        verify(restaurantService).findValidRestaurant(restaurantId);
    }

    @Test
    void shouldFindProductById() {
        var productId = UUID.randomUUID();
        var product = new Product(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertSame(product, productService.findValidProduct(productId));
        verify(productRepository).findById(productId);
    }
}
