package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PaymentMethodRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping
    public List<PaymentMethod> listAllResources() {
        return paymentMethodRepository.findAll();
    }

    @GetMapping("/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> findOneResource(@PathVariable UUID paymentMethodId) {
        var paymentMethod = paymentMethodService.findValidPaymentMethod(paymentMethodId);

        return ResponseEntity.ok(paymentMethod);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethod createResource(@RequestBody @Valid PaymentMethod paymentMethodCandidate) {
        try {
            return paymentMethodRepository.save(paymentMethodCandidate);
        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(
                    PaymentMethod.class,
                    paymentMethodCandidate.getDescription()
            );
        }
    }

    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> updateResource(@PathVariable UUID paymentMethodId,
                                                        @RequestBody @Valid PaymentMethod paymentMethodSource) {

        var updatedPaymentMethod = paymentMethodService.updateProperties(paymentMethodId, paymentMethodSource);

        return ResponseEntity.ok(updatedPaymentMethod);
    }

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID paymentMethodId) {
        try {
            paymentMethodRepository.deleteById(paymentMethodId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException(PaymentMethod.class, paymentMethodId.toString());
        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(PaymentMethod.class, paymentMethodId.toString());
        }
    }
}
