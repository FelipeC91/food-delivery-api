package com.mypersonalportifolio.food_delivery_api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @Column(name = "address_zip_code")
    private String zipCode;

    @Column(name = "address_street_name")
    private String streetName;

    @Column(name = "address_street_number")
    private String StreetNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id",  nullable = false)
    private City city;
}

