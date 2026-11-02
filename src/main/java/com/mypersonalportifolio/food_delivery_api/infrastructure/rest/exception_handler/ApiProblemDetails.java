package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.exception_handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(name = "ApiProblemDetails")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiProblemDetails {

    @Schema(example = "400")
    private Integer httpStatusCode;

    private String type;

    @Schema(example = "Review properties and syntax")
    private String detail;

    @Schema(example = "Resource contains a field with errors")
    private String title;

    @Schema(example = "2026-07-15T11:01:00")
    private OffsetDateTime timestamp;
    @Schema(example = "Fields with error")
    private List<InvalidResourceField> invalidResourceFields;

    @Schema(name = "InvalidResourceField")
    public record InvalidResourceField(
            @Schema(example = "price")
            String name,

             @Schema(example = "price must be positive")
             String validationMessage
    ){}
}
