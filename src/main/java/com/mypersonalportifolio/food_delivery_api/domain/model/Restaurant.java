package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;


import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends DomainEntityUUID {

    @NotBlank
    @Column(nullable = false)
    @Setter
    private String name;

    @PositiveOrZero
    @Column(name = "shipping_cost", nullable = false)
    @Setter
    private BigDecimal shippingCost;

    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.RestaurantRegistration.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_category_id", nullable = false)
    @Setter
    private FoodCategory foodCategory;

    @Embedded
    @Setter
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
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


    @OneToMany(mappedBy = "restaurant", fetch =  FetchType.LAZY)
    private List<Product> products = new ArrayList<>();


    @Column(name = "is_active")
    @Setter
    private Boolean active = Boolean.TRUE;

    public boolean isActive() {
        return (
                this.active &&
                Objects.nonNull( this.getAddress() ) &&
                !this.getPaymentMethods().isEmpty()
        );
    }

}
