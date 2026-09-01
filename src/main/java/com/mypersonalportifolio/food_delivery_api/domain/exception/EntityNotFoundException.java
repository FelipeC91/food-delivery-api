package com.mypersonalportifolio.food_delivery_api.domain.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class entityClass, @Nullable String triedStateReference) {
        super( String.format("%s referenciado (%s) não encontrado.", entityClass.getSimpleName(), triedStateReference));
    }
}
