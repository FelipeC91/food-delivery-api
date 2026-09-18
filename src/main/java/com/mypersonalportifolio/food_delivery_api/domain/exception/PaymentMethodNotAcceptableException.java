package com.mypersonalportifolio.food_delivery_api.domain.exception;

import jakarta.validation.constraints.NotBlank;

public class PaymentMethodNotAcceptableException extends BusinessConstraintsViolationException {
    public PaymentMethodNotAcceptableException(@NotBlank String paymentDescription, String restaurantTargetName) {
        super(String.format("Meio de pagamento %s não aceito no restaurante %s", paymentDescription, restaurantTargetName ));
    }
}
