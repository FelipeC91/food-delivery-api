package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters;

import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant.RestaurantProductController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.ProductRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.RestaurantLinkEngine;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductRepresentationModel> {

    public ProductRepresentationModelAssembler() {
        super(RestaurantProductController.class, ProductRepresentationModel.class);
    }

    @Override
    public ProductRepresentationModel toModel(Product entity) {
        var representationModel = new ProductRepresentationModel(entity);

        representationModel.add(RestaurantLinkEngine.slashProductsSlashProductId(entity.getId(),entity.getRestaurant().getId()));

        representationModel.add(RestaurantLinkEngine.slashProductsSlashProductIdSlashPhoto(entity.getId(),entity.getRestaurant().getId()));
        return representationModel;
    }

    @Override
    public CollectionModel<ProductRepresentationModel> toCollectionModel(Iterable<? extends Product> entities) {
        return super.toCollectionModel(entities)
                .add(RestaurantLinkEngine.SlashProducts(entities.iterator().next().getId()));
    }
}
