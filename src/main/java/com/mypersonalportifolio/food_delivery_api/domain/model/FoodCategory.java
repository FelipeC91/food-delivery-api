package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.ValidationGroups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "food_category")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCategory  extends DomainEntityUUID {

    @Pattern(regexp = "^[^0-9]*$", message = "Caracteres numéricos não são permitidos", groups = ValidationGroups.FoodCategory.class)
    @NotBlank(groups = ValidationGroups.FoodCategory.class)
    @Column(nullable = false)
    private String name;

    private String description;

//    @JsonIgnore
//    @OneToMany(mappedBy = "foodCategory")
//    private List<Restaurant> restaurants = new ArrayList<>();


    @NotBlank(groups = ValidationGroups.Restaurant.class, message = "ID da categoria inválido")
    public UUID getId() {
        return super.getId();
    }
}
