package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.CityInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.StateRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public CityService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    @Transactional
    public City create(CityInputDTO cityCandidate) {
        var validState = validateState(cityCandidate.stateId());

        if (cityRepository.existsByNameAndStateId(cityCandidate.name(), cityCandidate.stateId()))
            throw new EntityExistsException("City with name " + cityCandidate.name() + " already exists");

        var cityTarget = new City(cityCandidate.name(), validState);

        var savedCity = cityRepository.save(cityTarget);

        log.info("city {} has been created", savedCity.toString());

        return savedCity;
    }

    @Transactional
    public City updateProperties(CityInputDTO citySource, City cityTarget) {
        if (cityRepository.existsByNameAndStateId(citySource.name(), citySource.stateId()))
            throw new EntityExistsException("City with name " + citySource.name() + " already exists");

        cityTarget.setName(citySource.name());
        cityTarget.setState(validateState(citySource.stateId()));

        var savedCity = cityRepository.save(cityTarget);

        log.info("city {} has been updated", savedCity.toString());

        return savedCity;
    }

    private State validateState(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow( () -> new EntityNotFoundException(State.class, stateId.toString()));
    }

    public City findVerifiedCity(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow( () -> new EntityNotFoundException(City.class, cityId.toString()) );
    }

    public City findVerifiedCityByName(@NotBlank String name) {
        return cityRepository.findByName(name)
                .orElseThrow( () -> new EntityNotFoundException(City.class, name) );
    }
}
