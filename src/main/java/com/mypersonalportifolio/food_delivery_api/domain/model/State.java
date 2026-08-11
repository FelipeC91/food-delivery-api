package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntitySequence;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class State extends DomainEntitySequence {

    @Column(nullable = false)
    private String name;
}
