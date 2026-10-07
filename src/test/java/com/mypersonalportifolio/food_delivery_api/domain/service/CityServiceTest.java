package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.StateRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.input.CityInputDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    void shouldUpdateCityFromInputDtoAndUsePersistedState() {
        var stateId = 3L;
        var persistedState = org.mockito.Mockito.mock(State.class);
        var cityTarget = new City("Old name", null);
        var cityInput = new CityInputDTO("New name", stateId);
        when(stateRepository.findById(stateId)).thenReturn(Optional.of(persistedState));
        when(cityRepository.save(cityTarget)).thenReturn(cityTarget);

        var updatedCity = cityService.updateProperties(cityInput, cityTarget);

        assertSame(cityTarget, updatedCity);
        assertEquals("New name", updatedCity.getName());
        assertSame(persistedState, updatedCity.getState());
        verify(stateRepository).findById(stateId);
        verify(cityRepository).save(cityTarget);
    }
}
