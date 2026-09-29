package com.mypersonalportifolio.food_delivery_api.infrastructure.event_handler;

import com.mypersonalportifolio.food_delivery_api.application.email.MailMessageSource;
import com.mypersonalportifolio.food_delivery_api.application.email.MailSenderService;
import com.mypersonalportifolio.food_delivery_api.domain.event.OrderCancelledEvent;
import com.mypersonalportifolio.food_delivery_api.domain.event.OrderConfirmedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;

@Component
public class OnOrderCancelledListener {
    private final MailSenderService mailSenderService;

    public OnOrderCancelledListener(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notifyCustomerViaEmail(OrderCancelledEvent event) {
        var orderTarget = event.order();

        var mail = MailMessageSource.builder()
                .to(Set.of(orderTarget.getCustomer().getEmail()))
                .subject(orderTarget.getRestaurant().getName())
                .body("order-cancelled.html")
                .modelVariable("order", orderTarget)
                .build();

        mailSenderService.sendMail(mail);
    }
}
