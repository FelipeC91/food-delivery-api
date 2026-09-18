package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderInputDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.server.DelegatingServerHttpResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Entity(name = "order_model")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Order extends DomainEntityUUID {

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @Setter
    private User customer;


    @AttributeOverrides({
            @AttributeOverride(name = "neighborhood", column = @Column(name = "customer_address_neighborhood")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "customer_address_zip_code")),
            @AttributeOverride(name = "streetName", column = @Column(name = "customer_address_street_name")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "customer_address_street_number"))
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
    private Set<OrderItem> items = new HashSet<>();

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

    public static Order confirmNewOrder(Restaurant restaurant, PaymentMethod paymentMethod, User customer, Address shippingAddress, Set<OrderItem> items) {
        var subtotal = calculateSubtotal(items);

        return new Order(
                    restaurant,
                    paymentMethod,
                    customer,
                    shippingAddress,
                    restaurant.getShippingCost(),
                    OrderStatus.CRIADO,
                    items,
                    subtotal,
                    subtotal.add(restaurant.getShippingCost()),
                    OffsetDateTime.now(),
                    null,
                    null,
                    null

        );
    }
    private static BigDecimal calculateSubtotal(Collection<OrderItem> items) {
        return items.stream().map(i -> i.getProduct().getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void setStatus(OrderStatus newOrderStatus, OrderStatus previousMustBe) {
        if ( previousMustBe == this.status ) {
            this.status = newOrderStatus;

        } else {
            var message = String.format("Status do pedido %s, estand oem %s não pode ser alterado para %s", this.getId().toString(), this.getStatus(), newOrderStatus);
            throw new BusinessConstraintsViolationException(message);
        }
    }
    public void confirm() {
        setStatus(OrderStatus.CONFIRMADO, OrderStatus.CRIADO);
        this.confirmedAt = OffsetDateTime.now();

    }    public void completeDelivery() {
        setStatus(OrderStatus.ENTREGUE, OrderStatus.CONFIRMADO);
        this.confirmedAt = OffsetDateTime.now();

    }


    public void cancel() {
        setStatus(OrderStatus.CANCELADO, OrderStatus.CRIADO);
        this.cancelledAt = OffsetDateTime.now();
    }


}
