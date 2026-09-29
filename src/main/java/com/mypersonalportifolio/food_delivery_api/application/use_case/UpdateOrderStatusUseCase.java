package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.email.MailMessageSource;
import com.mypersonalportifolio.food_delivery_api.application.email.MailSenderService;
import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.OrderService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.OrderStatusInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UpdateOrderStatusUseCase implements UseCase<OrderStatusInputDTO, Void> {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public UpdateOrderStatusUseCase(OrderService orderService,
                                    OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Void execute(OrderStatusInputDTO inputDTO) {
        var orderTarget = orderService.findValidOrder(inputDTO.getOrderId());

        switch (inputDTO.getOrderStatus()) {
            case CONFIRMADO -> {
                orderTarget.confirm();

                orderRepository.save(orderTarget); //it only to fire a domain event
            }
            case ENTREGUE -> orderTarget.completeDelivery();

            case CANCELADO -> {
                orderTarget.cancel();

                orderRepository.save(orderTarget); //it only to fire a domain event
            }
        }

        return null;
    }
}
