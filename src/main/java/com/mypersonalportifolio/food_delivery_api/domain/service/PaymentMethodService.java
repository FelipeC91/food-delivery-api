package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityAlreadyExistsException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PaymentMethodRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public PaymentMethod updateProperties(UUID paymentMethodTargetId,
                                           PaymentMethod paymentMethodSource) {

        if (paymentMethodRepository.existsByDescription(paymentMethodSource.getDescription()))
            throw new EntityAlreadyExistsException(paymentMethodSource.getClass(), paymentMethodSource.getDescription());

        var paymentMethodTarget= findValidPaymentMethod(paymentMethodTargetId);

        paymentMethodTarget.setDescription(paymentMethodSource.getDescription());

        var updatedPaymentMethod = paymentMethodRepository.saveAndFlush(paymentMethodTarget);

        log.info("Payment method {} updated successfully", updatedPaymentMethod.toString());

        return  updatedPaymentMethod;
    }

    public PaymentMethod findValidPaymentMethod(UUID paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new EntityNotFoundException(PaymentMethod.class, paymentMethodId.toString()));
    }

    public PaymentMethod validateAndSave(@Valid PaymentMethod paymentMethodCandidate) {
        try {

            if (paymentMethodRepository.existsByDescription(paymentMethodCandidate.getDescription()))
                throw new EntityAlreadyExistsException(paymentMethodCandidate.getClass(), paymentMethodCandidate.getDescription());

            return paymentMethodRepository.save(paymentMethodCandidate);

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(PaymentMethod.class, paymentMethodCandidate.getDescription());
        }
    }
}
