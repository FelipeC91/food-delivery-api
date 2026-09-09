package com.mypersonalportifolio.food_delivery_api.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class FailOnValidateEntityPropertiesException extends RuntimeException {
    private BindingResult bindingResult;
    private String entityName;
}
