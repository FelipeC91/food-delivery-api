package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record OrderInputDTO(
        @NotNull
        UUID restaurantId,

        @NotNull
        UUID paymentMethodId,

        @Valid
        @NotNull
        AddressInputDTO shippingAddress,

        @Valid
        @NotNull
        List<OrderItemInputDTO> items
) {

    public record OrderItemInputDTO(
            @NotNull
            UUID productId,

            @Positive
            Integer quantity,

            String note
    ) { }

}
