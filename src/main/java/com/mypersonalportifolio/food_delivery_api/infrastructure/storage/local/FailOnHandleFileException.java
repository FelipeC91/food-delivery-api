package com.mypersonalportifolio.food_delivery_api.infrastructure.storage.local;

public class FailOnHandleFileException extends RuntimeException {
    public FailOnHandleFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
