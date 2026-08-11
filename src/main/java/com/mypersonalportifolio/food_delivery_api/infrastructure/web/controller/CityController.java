package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;


import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<City> findOneResource(@PathVariable Long cityID) {
        var cityOptional = cityRepository.findById(cityID);

        if (cityOptional.isPresent())
            return ResponseEntity.ok(cityOptional.get());

        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createResource(@RequestBody City cityCandidate){
        try {
            cityService.create(cityCandidate);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (EntityNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @PutMapping("/{cityId}")
    public ResponseEntity<?> updateResource(@PathVariable Long cityId,
                                                    @RequestBody City citySource) {
        try {
            var cityTargetOptional = cityRepository.findById(cityId);

            if (cityTargetOptional.isPresent()) {
                BeanUtils.copyProperties(citySource, cityTargetOptional.get(), "id");

                var cityNewState = cityRepository.saveAndFlush(cityTargetOptional.get());

                return ResponseEntity.ok(cityNewState);

            } else
                return ResponseEntity.notFound().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/cityId")
    public ResponseEntity<City> deleteResource(@PathVariable Long cityId) {
        try {
            cityRepository.deleteById(cityId);

            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }
    }

}
