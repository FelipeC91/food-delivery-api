package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
public class ProductRepresentationModel extends RepresentationModel<ProductRepresentationModel> {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final boolean active;

    public ProductRepresentationModel(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.active = product.isActive();
    }
}
