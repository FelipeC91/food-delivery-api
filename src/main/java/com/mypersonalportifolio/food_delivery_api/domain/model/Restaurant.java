package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Address;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends DomainEntityUUID {

    @Column(nullable = false)
    private String name;

    @Column(name = "shipping_cost", nullable = false)

    private BigDecimal shippingCost;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
                joinColumns = @JoinColumn(name = "restaurant_id"),
                inverseJoinColumns = @JoinColumn( name = "payment_method_id")
    )
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime updatedAt;

}
