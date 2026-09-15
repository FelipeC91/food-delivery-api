package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "order_model")
@Getter
public class Order extends DomainEntityUUID {

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    private User customer;


    @AttributeOverrides({
            @AttributeOverride(name = "neighborhood",column = @Column(name = "customer_address_neighborhood")),
            @AttributeOverride(name = "zipCode",column = @Column(name = "customer_address_zip_code")),
            @AttributeOverride(name = "streetName",column = @Column(name = "customer_address_street_name")),
            @AttributeOverride(name = "streetNumber",column = @Column(name = "customer_address_street_number"))
    })
    @AssociationOverride(name = "city", joinColumns = @JoinColumn(name = "customer_address_city_id"))
    @Embedded
    private Address shippingAddress;

    @Column(name = "shipping_cost")
    private BigDecimal shippingCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @ElementCollection
    @CollectionTable(name = "order_item",
                    joinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderItem> items = new ArrayList<>();

    private BigDecimal subtotal;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "confirmed_at")
    private OffsetDateTime confirmedAt;

    @Column(name = "cancelled_at")
    private OffsetDateTime cancelledAt;

    @Column(name = "delivered_in")
    private OffsetDateTime DeliveredIn;
}
