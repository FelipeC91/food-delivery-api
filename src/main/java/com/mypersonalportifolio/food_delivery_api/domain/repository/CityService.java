package com.mypersonalportifolio.food_delivery_api.domain.repository;

import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    public City create(City city) {
        var stateId = city.getEstado().getId();

        var state = stateRepository.findById(stateId)
                        .orElseThrow( () -> new EntityNotFoundException(
                                                String.format("Não existe Estado com essa referencia de Id: %d", stateId)
                                            )
                        );

        city.setEstado(state);


        return cityRepository.save(city);

    }
}
