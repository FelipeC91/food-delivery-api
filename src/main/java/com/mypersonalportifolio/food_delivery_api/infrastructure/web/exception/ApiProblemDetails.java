package com.mypersonalportifolio.food_delivery_api.domain.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiProblemDetails {

    private Integer httpStatusCode;
    private String type;
    private String detail;
    private String title;
    private OffsetDateTime timestamp;
}
