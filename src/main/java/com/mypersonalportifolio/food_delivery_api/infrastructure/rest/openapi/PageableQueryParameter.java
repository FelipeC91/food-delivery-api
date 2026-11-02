package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
        in = ParameterIn.QUERY,
        name = "page",
        description = "Page number (0..N)",
        schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "size",
        description = "items per page",
        schema = @Schema(type = "integer", defaultValue = "10")
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "sort",
        description = "Ordination criteria: propriedade(ASC|DESC).",
        schema = @Schema(type = "string", defaultValue = "name,ASC"),
        examples = {
                @ExampleObject("name"),
                @ExampleObject("name,ASC"),
                @ExampleObject("name,DESC")
        }
)
public @interface PageableQueryParameter {
}
