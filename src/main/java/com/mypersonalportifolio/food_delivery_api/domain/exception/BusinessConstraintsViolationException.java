package com.mypersonalportifolio.food_delivery_api.domain.exception;


public class BusinessConstraintsViolationException extends RuntimeException {
    public BusinessConstraintsViolationException(String message) {}
    public BusinessConstraintsViolationException(String message, Throwable cause) {}
}
