package com.mypersonalportifolio.food_delivery_api.domain.exception;

import org.jspecify.annotations.Nullable;

public class NonExistentEntityException extends RuntimeException {

    public NonExistentEntityException(Class entityClass, @Nullable String triedStateReference) {
        super( String.format("%s referenciado (%s) não existe.", entityClass.getName(), triedStateReference));
    }
}
