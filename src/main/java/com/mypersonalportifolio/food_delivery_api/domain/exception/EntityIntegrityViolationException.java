package com.mypersonalportifolio.food_delivery_api.domain.exception;

import org.jspecify.annotations.Nullable;

public class EntityIntegrityViolationException extends BusinessConstraintsViolationException {

    public EntityIntegrityViolationException(Class entityClass, @Nullable String triedStateReference) {
        super( String.format("%s referenciado possui associação com outras entidades.", entityClass.getSimpleName(), triedStateReference));
    }
}
