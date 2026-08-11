package com.mypersonalportifolio.food_delivery_api.domain.concept;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@MappedSuperclass

 @NoArgsConstructor
 @AllArgsConstructor
 @Getter
 @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class DomainEntityUUID {

     @Id
     @GeneratedValue
     @UuidGenerator
     @EqualsAndHashCode.Include
    private UUID id;
}
