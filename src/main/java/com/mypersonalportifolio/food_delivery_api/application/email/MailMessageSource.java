package com.mypersonalportifolio.food_delivery_api.application.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class MailMessageSource {

    private  Set<String> to;
    private final String subject;
    private final String body;

    @Singular
    private final Map<String, Object> modelVariables;
}
