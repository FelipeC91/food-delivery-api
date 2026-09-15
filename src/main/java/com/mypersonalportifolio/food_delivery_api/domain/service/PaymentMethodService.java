package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public PaymentMethod updateProperties(UUID paymentMethodTargetId,
                                           PaymentMethod paymentMethodSource) {
        var paymentMethodTarget= findValidPaymentMethod(paymentMethodTargetId);

        paymentMethodTarget.setDescription(paymentMethodSource.getDescription());

        return paymentMethodRepository.saveAndFlush(paymentMethodTarget);
    }

    public PaymentMethod findValidPaymentMethod(UUID paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new EntityNotFoundException(PaymentMethod.class, paymentMethodId.toString()));
    }
}
