package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntitySequenceID;
import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class City extends DomainEntitySequenceID {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties(value = "name", allowSetters = true)
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.CityRegistration.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
