package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "food_category")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCategory  extends DomainEntityUUID {

    @Column(nullable = false)
    private String name;

    private String description;

//    @JsonIgnore
//    @OneToMany(mappedBy = "foodCategory")
//    private List<Restaurant> restaurants = new ArrayList<>();

}
