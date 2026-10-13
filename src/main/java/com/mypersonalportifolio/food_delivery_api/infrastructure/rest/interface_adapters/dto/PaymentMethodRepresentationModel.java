package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(value = "restaurant", collectionRelation = "restaurants")
@Getter
@EqualsAndHashCode(callSuper = true)
public class PaymentMethodRepresentationModel extends RepresentationModel<PaymentMethodRepresentationModel> {
    private final UUID id;
    private final String description;

    public  PaymentMethodRepresentationModel(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.description = paymentMethod.getDescription();
    }

}
