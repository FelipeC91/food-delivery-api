package com.mypersonalportifolio.food_delivery_api.domain.exception;

public class PendingOrderAlreadyExistsException extends BusinessConstraintsViolationException {
    public PendingOrderAlreadyExistsException() {
        super("Cliente possui Pedidos em andamento");
    }
}
