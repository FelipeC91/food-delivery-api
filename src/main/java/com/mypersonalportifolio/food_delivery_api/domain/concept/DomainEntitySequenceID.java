package com.mypersonalportifolio.food_delivery_api.domain.concept;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@MappedSuperclass
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DomainEntitySequence {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DomainEntitySequence that = (DomainEntitySequence) o;
        return Objects.equals(id, that.id);
    }
}
