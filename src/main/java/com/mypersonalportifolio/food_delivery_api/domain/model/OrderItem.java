package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
//@Entity(name = "order_item")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem //extends DomainEntityUUID {
{
    @ManyToOne
    @JoinColumn(name = "product_id")
    @Setter
    private Product product;

    private Integer quantity;

    @Column(name = "unit_price")
    @Setter
    private BigDecimal unitPrice;

    @Setter
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    private String note;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Order order;

}
