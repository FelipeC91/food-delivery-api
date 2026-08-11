package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "food_category")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCategory  extends DomainEntityUUID {

    @Column(nullable = false)
    private String name;

//    @JsonIgnore
//    @OneToMany(mappedBy = "food_category")
//    private List<Restaurant> restaurants = new ArrayList<>();

}
