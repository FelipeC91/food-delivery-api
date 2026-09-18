package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderStatusInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrderStatusUseCase implements UseCase<OrderStatusInputDTO, Void> {

    @Autowired
    OrderService orderService;

    @Transactional
    @Override
    public Void execute(OrderStatusInputDTO inputDTO) {
        var orderTarget = orderService.findValidOrder(inputDTO.getOrderId());

        switch (inputDTO.getOrderStatus()) {
            case CONFIRMADO -> orderTarget.confirm();

            case ENTREGUE -> orderTarget.completeDelivery();

            case CANCELADO -> orderTarget.cancel();
        }

        return null;
    }
}
