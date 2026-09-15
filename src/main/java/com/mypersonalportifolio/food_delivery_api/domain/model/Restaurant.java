package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;


import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id", nullable = false)
    @Setter
    private FoodCategory foodCategory;

    @Embedded
    @Setter
    private Address address;

    @Getter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id")
    )
    private Set<PaymentMethod> paymentMethods = new HashSet<PaymentMethod>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<Product>();


    @Column(name = "is_active")
    @Setter
    private Boolean active = Boolean.TRUE;


    @Column(name = "is_open")
    private Boolean open = Boolean.FALSE;

    public boolean isActive() {
        return (
                this.active &&
                        Objects.nonNull(this.getAddress()) &&
                        !this.getAllowedPaymentMethods().isEmpty()
        );
    }

    @ManyToMany
    @JoinTable(name = "restaurant_user",
                joinColumns = @JoinColumn(name = "restaurant_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> managers = new HashSet<>();

    public Set<User> getManagers() {
        return Collections.unmodifiableSet(managers);
    }

    public boolean addManager(User user) {
        return managers.add(user);
    }

    public boolean removeManager(User user) {
        return managers.remove(user);
    }

    public boolean isOpen() {
        return this.open;
    }

    public Set<PaymentMethod> getAllowedPaymentMethods() {
        return Collections.unmodifiableSet(this.paymentMethods);
    }

    public boolean attachAllowedPaymentMethod(PaymentMethod paymentMethod) {
        return this.paymentMethods.add(paymentMethod);
    }

    public void detachAllowedPaymentMethod(PaymentMethod paymentMethod) {

        this.paymentMethods.remove(paymentMethod);
    }

    public Set<Product> getProductCatalog() {
        return Collections.unmodifiableSet(this.products);
    }

}

