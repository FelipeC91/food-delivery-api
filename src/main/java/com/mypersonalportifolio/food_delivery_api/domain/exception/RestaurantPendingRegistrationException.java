package com.mypersonalportifolio.food_delivery_api.domain.exception;

public class RestaurantPendingRegistrationException extends BusinessConstraintsViolationException {
    public RestaurantPendingRegistrationException() {
        super(String.format("Cadastros de endereço formas de pagamento podem estar pendentes. Revise estes cadastros antes de tentar novamente"));
    }

}
