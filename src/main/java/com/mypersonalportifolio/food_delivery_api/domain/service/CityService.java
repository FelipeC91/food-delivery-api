package com.mypersonalportifolio.food_delivery_api.domain.repository;

import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import jakarta.persistence.EntityNotFoundException;
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

    public City create(City city) {
        var stateId = city.getEstado().getId();

        var state = stateRepository.findById(stateId)
                        .orElseThrow( () -> new EntityNotFoundException( String.format(stateId.toString() ) ));

        city.setEstado(state);

        return cityRepository.save(city);

    }

    @Transactional
    public City updateProperties(City citySource, City cityTarget) {
        BeanUtils.copyProperties(citySource, cityTarget, "id");

        return cityRepository.save(cityTarget);
    }
}
