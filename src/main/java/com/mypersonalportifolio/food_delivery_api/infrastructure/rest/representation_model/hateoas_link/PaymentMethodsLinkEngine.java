package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.PaymentMethodController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class PaymentMethodsLinkEngine {

    public static Link slashPaymentMethods() {
        return WebMvcLinkBuilder.linkTo(PaymentMethodController.class).withRel("/payment-methods");
    }

    public static Link slashPaymentMethodsSlashPaymentMethodId(UUID paymentMethodId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class).findSingleResource(paymentMethodId)).withSelfRel();
    }
}
