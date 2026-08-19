package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "payment_method")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod extends DomainEntityUUID {

    @Column(nullable = false)
    private String description;
}
