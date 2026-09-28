package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@SecondaryTable(
        name = "product_photo",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_id")
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends DomainEntityUUID {

    @NotBlank
    @Column(nullable = false)
    @Setter
    private String name;

    @NotBlank
    @Column(nullable = false)
    @Setter
    private String description;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    @Setter
    private BigDecimal price;


    @Column(name = "is_active", nullable = false)
    @Setter
    private boolean active;

    @Setter
    @Embedded
    private ProductPhoto photoMetaData;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "restaurant_id",nullable = false)
    @Setter
    private Restaurant restaurant;


    public Product(UUID id) {
        super(id);
    }
}
