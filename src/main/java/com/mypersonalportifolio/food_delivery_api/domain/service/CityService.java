package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.NonExistentEntityException;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.State;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    public City create(City city) {
        var validState = validateState(city);

        city.setEstado(validState);

        return cityRepository.save(city);

    }

    public City updateProperties(City citySource, City cityTarget) {
        BeanUtils.copyProperties(citySource, cityTarget, "id");

        var validState = validateState(citySource);

        cityTarget.setEstado(validState);

        return cityRepository.save(cityTarget);
    }

    private State validateState(City city) {
        var stateId = city.getEstado().getId();

        return stateRepository.findById(stateId)
                .orElseThrow( () -> new EntityNotFoundException(State.class, stateId.toString()));
    }
}
