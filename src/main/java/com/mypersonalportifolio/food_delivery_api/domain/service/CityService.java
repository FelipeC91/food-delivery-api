package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.StateRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
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
    public City create(City city) {
        var validState = validateState(city);

        city.setState(validState);

        return cityRepository.save(city);

    }

    @Transactional
    public City updateProperties(City citySource, City cityTarget) {
        BeanUtils.copyProperties(citySource, cityTarget, "id");

        var validState = validateState(citySource);

        cityTarget.setState(validState);

        return cityRepository.save(cityTarget);
    }

    private State validateState(City city) {
        var stateId = city.getState().getId();

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
