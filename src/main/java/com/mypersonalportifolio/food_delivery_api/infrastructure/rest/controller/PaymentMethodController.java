package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PaymentMethodRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.PaymentMethodService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.PaymentMethodRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.PaymentMethodRepresentationModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodService paymentMethodService;
    private final PaymentMethodRepresentationModelAssembler assembler;

    public PaymentMethodController(PaymentMethodRepository paymentMethodRepository, PaymentMethodService paymentMethodService, PaymentMethodRepresentationModelAssembler assembler) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodService = paymentMethodService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<PaymentMethodRepresentationModel> listAllResources() {
        return assembler.toCollectionModel(paymentMethodRepository.findAll());
    }

    @GetMapping("/{paymentMethodId}")
    public ResponseEntity<PaymentMethodRepresentationModel> findSingleResource(@PathVariable UUID paymentMethodId) {
        var paymentMethod = assembler.toModel(paymentMethodService.findValidPaymentMethod(paymentMethodId));

        return ResponseEntity.ok(paymentMethod);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodRepresentationModel createResource(@RequestBody @Valid PaymentMethod paymentMethodCandidate) {
        try {
            return assembler.toModel(paymentMethodRepository.save(paymentMethodCandidate));

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(
                    PaymentMethod.class,
                    paymentMethodCandidate.getDescription()
            );
        }
    }

    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<PaymentMethodRepresentationModel> updateResource(@PathVariable UUID paymentMethodId,
                                                        @RequestBody @Valid PaymentMethod paymentMethodSource) {

        var updatedPaymentMethod = assembler.toModel(paymentMethodService.updateProperties(paymentMethodId, paymentMethodSource));

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
