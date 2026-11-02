package com.mypersonalportifolio.food_delivery_api.application.use_case.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class OrderInputDTO {

    @Schema(example = "f1b460c9-dcc0-4ec3-bb72-deec95803e99")
    @NotNull
    private final UUID restaurantId;


    @Schema(example = "d53d294e-9b40-11f1-b8aa-a2d63dc49d10")
    @NotNull
    private final UUID paymentMethodId;
;
    @Valid
    @NotNull
    private final AddressInputDTO shippingAddress;

    @Valid
    @Size(min = 1)
    private final List<OrderItemInputDTO> items;

    @Schema(hidden = true)
    @Setter
    private UUID customerId;

    public record OrderItemInputDTO(

            @Schema(example = "2a8f7c6e-1a2b-4d3e-9f0a-111111111111")
            @NotNull
            UUID productId,

            @Schema(example = "2")
            @Positive
            Integer quantity,

            String note
    ) {}
}
