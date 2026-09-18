package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product associateRestaurant(Product product, Restaurant restaurantTarget) {

        if(!productRepository.existsById( product.getId() ))
            throw new EntityNotFoundException(Product.class, "Produto");

        if (restaurantTarget.getProductCatalog().contains(product))
            throw new BusinessConstraintsViolationException("Produto já associado ao restaurante");

        product.setRestaurant(restaurantTarget);

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProperties(Product productTarget, Product productSource) {
        BeanUtils.copyProperties(productTarget, productSource, "id", "restaurant");

        return productRepository.saveAndFlush(productTarget);
    }

    public Product findValidProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, "Produto"));
    }
}
