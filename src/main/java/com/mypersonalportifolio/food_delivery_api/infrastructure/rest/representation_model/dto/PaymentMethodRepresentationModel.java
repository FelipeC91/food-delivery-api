package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PaymentMethodRepresentationModel extends RepresentationModel<PaymentMethodRepresentationModel> {
    private UUID id;
    private String description;
}
