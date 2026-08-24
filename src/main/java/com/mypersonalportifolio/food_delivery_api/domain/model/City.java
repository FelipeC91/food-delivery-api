package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntitySequenceID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class City extends DomainEntitySequenceID {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @PrimaryKeyJoinColumn
//    @JoinColumn(name = "state_id", nullable = false)
    @Setter
    private State estado;
}
