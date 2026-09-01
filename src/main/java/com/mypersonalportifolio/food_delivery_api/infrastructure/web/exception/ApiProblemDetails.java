package com.mypersonalportifolio.food_delivery_api.infrastructure.web.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiProblemDetails {

    private Integer httpStatusCode;
    private String type;
    private String detail;
    private String title;
    private OffsetDateTime timestamp;
    private List<InvalidResourceField> invalidResourceFields;

    public record InvalidResourceField(
         String name,
         String validationMessage
    ){}
}
