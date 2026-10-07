package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.StateRepository;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.input.CityInputDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public City create(CityInputDTO cityCandidate) {
        var validState = validateState(cityCandidate.stateId());

        if (cityRepository.existsByName(cityCandidate.name()))
            throw new EntityExistsException("City with name " + cityCandidate.name() + " already exists");

        var cityTarget = new City(cityCandidate.name(), validState);
        return cityRepository.save(cityTarget);

    }

    @Transactional
    public City updateProperties(CityInputDTO citySource, City cityTarget) {
        cityTarget.setName(citySource.name());
        cityTarget.setState(validateState(citySource.stateId()));

        return cityRepository.save(cityTarget);
    }

    private State validateState(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow( () -> new EntityNotFoundException(State.class, stateId.toString()));
    }

    public City findValidCity(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow( () -> new EntityNotFoundException(City.class, cityId.toString()) );
    }

    public City findValidCityByName(@NotBlank String name) {
        return cityRepository.findByName(name)
                .orElseThrow( () -> new EntityNotFoundException(City.class, name) );
    }
}
