package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;


import com.mypersonalportifolio.food_delivery_api.domain.exception.NonExistentEntityException;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.CityService;
import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/cities")
@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<City> listAllResources() {
        return cityRepository.findAll();
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> findOneResource(@PathVariable Long cityId) {
        var city = cityRepository.findById(cityId)
                                        .orElseThrow( () -> new EntityNotFoundException(City.class, cityId.toString()) );

        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<?> createResource(@RequestBody City cityCandidate){

            cityService.create(cityCandidate);

            return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public City updateResource(@PathVariable Long cityId,
                                                    @RequestBody City citySource) {
        var cityTarget = cityRepository.findById(cityId)
                .orElseThrow( () -> new EntityNotFoundException(City.class, cityId.toString()) );


        return cityService.updateProperties(citySource, cityTarget);
    }

    @DeleteMapping("/cityId")
    public ResponseEntity<City> deleteResource(@PathVariable Long cityId) {
        cityRepository.deleteById(cityId);

        return ResponseEntity.noContent().build();
    }

}
