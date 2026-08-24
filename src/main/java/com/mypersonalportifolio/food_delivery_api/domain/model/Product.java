package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class Product extends DomainEntityUUID {

    private String name;
    private String description;
    private BigDecimal price;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    //@JoinColumn(name= "restaurant_id",nullable = false)
    private Restaurant restaurant;
}
