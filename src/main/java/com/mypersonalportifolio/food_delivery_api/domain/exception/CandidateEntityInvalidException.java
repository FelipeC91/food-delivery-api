package com.mypersonalportifolio.food_delivery_api.domain.exception;

import org.jspecify.annotations.Nullable;

public class CandidateEntityInvalidException extends RuntimeException {
    public CandidateEntityInvalidException(Class entityClass, @Nullable String triedStateReference) {
        super( String.format("recurso %s referenciado (%s) pussui dados inconsistentes.", entityClass.getSimpleName(), triedStateReference));
    }
}
