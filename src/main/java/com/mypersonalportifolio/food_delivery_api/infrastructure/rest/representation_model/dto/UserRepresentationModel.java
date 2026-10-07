package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class UserRepresentationModel extends RepresentationModel<UserRepresentationModel> {
    private String name;
    private String email;
    private OffsetDateTime createdAt;
}