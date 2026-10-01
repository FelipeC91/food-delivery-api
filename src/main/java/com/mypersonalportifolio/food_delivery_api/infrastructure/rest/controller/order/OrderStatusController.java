package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.order;

import com.mypersonalportifolio.food_delivery_api.application.use_case.UpdateOrderStatusUseCase;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderStatusInputDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/orders/{orderId}/status")
@RestController
public class OrderStatusController {

    @Autowired
    UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void updateResourceProperty(@PathVariable UUID orderId, @Valid @RequestBody OrderStatusInputDTO orderStatusInputDTO) {

        orderStatusInputDTO.setOrderId(orderId);
        updateOrderStatusUseCase.execute(orderStatusInputDTO);
    }
}
