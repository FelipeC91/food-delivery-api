package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntitySequenceID;
import com.sun.istack.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class State extends DomainEntitySequenceID {

    @Column(nullable = false)
    private String name;
}
