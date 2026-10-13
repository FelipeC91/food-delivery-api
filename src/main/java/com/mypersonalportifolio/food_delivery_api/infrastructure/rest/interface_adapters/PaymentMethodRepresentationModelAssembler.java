package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters;

import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.FoodCategoryController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.PaymentMethodRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.PaymentMethodsLinkEngine;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.RestaurantLinkEngine;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMethodRepresentationModelAssembler extends RepresentationModelAssemblerSupport<PaymentMethod, PaymentMethodRepresentationModel> {
    public PaymentMethodRepresentationModelAssembler() {
        super(FoodCategoryController.class, PaymentMethodRepresentationModel.class);
    }

    @Override
    public PaymentMethodRepresentationModel toModel(PaymentMethod entity) {
        var representationModel =  new PaymentMethodRepresentationModel(entity);

        representationModel.add(PaymentMethodsLinkEngine.slashPaymentMethods());
        representationModel.add(PaymentMethodsLinkEngine.slashPaymentMethodsSlashPaymentMethodId(entity.getId()));

        return representationModel;
    }



    @Override
    public CollectionModel<PaymentMethodRepresentationModel> toCollectionModel(Iterable<? extends PaymentMethod> entities) {
        return super.toCollectionModel(entities)
                .add(PaymentMethodsLinkEngine.slashPaymentMethods());
    }

    public CollectionModel<PaymentMethodRepresentationModel> toCollectionModel(Iterable<? extends PaymentMethod> entities, UUID restaurantId, UUID paymentMethodId) {
        return super.toCollectionModel(entities)
                .add(RestaurantLinkEngine.slashRestaurantsSlashPaymentMethods(restaurantId))
                .add(RestaurantLinkEngine.slashRestaurantsSlashPaymentMethod(restaurantId, paymentMethodId));
    }
}
