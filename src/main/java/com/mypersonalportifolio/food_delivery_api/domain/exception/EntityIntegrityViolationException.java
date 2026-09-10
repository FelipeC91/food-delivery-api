package com.mypersonalportifolio.food_delivery_api.domain.exception;

import org.jspecify.annotations.Nullable;

public class EntityIntegrityViolationException extends RuntimeException {

    public EntityIntegrityViolationException(Class entityClass, @Nullable String triedStateReference) {
        super( String.format("%s referenciado possui associação com outras entidades.", entityClass.getName(), triedStateReference));
    }
}
