package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input;

import com.mypersonalportifolio.food_delivery_api.domain.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class OrderStatusInputDTO {

        @Setter
        private UUID orderId;

        @NotNull
        private final OrderStatus orderStatus;
}
