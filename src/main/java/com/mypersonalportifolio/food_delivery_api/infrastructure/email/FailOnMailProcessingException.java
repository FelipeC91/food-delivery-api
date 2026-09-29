package com.mypersonalportifolio.food_delivery_api.infrastructure.email;

public class FailOnMailProcessingException extends RuntimeException {
    private static final String  ERROR_MESSAGE = "Não foi possivel enviar o email";

    public FailOnMailProcessingException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
