package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;

@Relation(value = "user", collectionRelation = "users")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRepresentationModel extends RepresentationModel<UserRepresentationModel> {
    private String name;
    private String email;
    private OffsetDateTime createdAt;
}