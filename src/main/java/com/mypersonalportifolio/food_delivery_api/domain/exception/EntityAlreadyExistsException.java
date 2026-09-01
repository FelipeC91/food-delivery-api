package com.mypersonalportifolio.food_delivery_api.domain.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityAlreadyExistsException extends RuntimeException  {
    public EntityAlreadyExistsException(Class entityClass, @Nullable String triedStateReference) {
        super( String.format("entidade %s referenciada (%s) já existe.", entityClass.getName(), triedStateReference));
    }
}
