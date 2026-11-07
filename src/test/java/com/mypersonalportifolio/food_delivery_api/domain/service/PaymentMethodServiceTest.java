package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PaymentMethodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceTest {

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private PaymentMethodService paymentMethodService;

    @Test
    void shouldSavePaymentMethodWhenDescriptionIsAvailable() {
        var candidate = new PaymentMethod("Credit card");
        when(paymentMethodRepository.existsByDescription(candidate.getDescription())).thenReturn(false);
        when(paymentMethodRepository.save(candidate)).thenReturn(candidate);

        var result = paymentMethodService.validateAndSave(candidate);

        assertSame(candidate, result);
        verify(paymentMethodRepository).existsByDescription(candidate.getDescription());
        verify(paymentMethodRepository).save(candidate);
    }
}
