package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order;

import com.mypersonalportifolio.food_delivery_api.application.use_case.UpdateOrderStatusUseCase;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.input.OrderStatusInputDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/orders/{orderId}/status")
@RestController
public class OrderStatusController {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderStatusController(UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public ResponseEntity<Void> updateResourceProperty(@PathVariable UUID orderId, @Valid @RequestBody OrderStatusInputDTO orderStatusInputDTO) {

        orderStatusInputDTO.setOrderId(orderId);
        updateOrderStatusUseCase.execute(orderStatusInputDTO);

        return ResponseEntity.noContent().build();
    }
}
