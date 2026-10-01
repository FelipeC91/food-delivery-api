package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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
        @Size(min = 1)
        List<OrderItemInputDTO> items
) {

    public record OrderItemInputDTO(
            @NotNull
            UUID productId,

            @Positive
            Integer quantity,

            String note
    ) {


    }





}
