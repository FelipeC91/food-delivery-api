package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.FoodCategoryService;
import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/food-categories")
public class FoodCategoryController {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private FoodCategoryService foodCategoryService;

    @GetMapping
    public List<FoodCategory> listAllResources() {
        return foodCategoryRepository.findAll();
    }

    @GetMapping("/{foodCategoryId}")
    public ResponseEntity<FoodCategory> findOneResource(@PathVariable UUID foodCategoryId) {
        var foodCategory = foodCategoryRepository.findById(foodCategoryId)
                                            .orElseThrow( () -> new EntityNotFoundException(FoodCategory.class, foodCategoryId.toString()) );

        return ResponseEntity.ok(foodCategory);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodCategory createResource(@RequestBody FoodCategory foodCategoryCandidate) {
        try {
            return foodCategoryRepository.save(foodCategoryCandidate);

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(FoodCategory.class, foodCategoryCandidate.getName());

        }
    }

    @PutMapping("/{foodCategoryId}")
    public ResponseEntity<?> updateResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId,
                                                @RequestBody @Valid FoodCategory foodCategorySource) {
        var foodCategoryTarget = foodCategoryRepository.findById(foodCategoryTargetId)
                .orElseThrow( () -> new EntityNotFoundException(FoodCategory.class,foodCategoryTargetId.toString() ));

        var updatedFoodCategory=  foodCategoryService.updateProperties(foodCategoryTarget, foodCategorySource);

        return ResponseEntity.status(HttpStatus.OK).body(updatedFoodCategory);

    }

    @DeleteMapping("/{foodCategoryId}")
    public ResponseEntity<?> deleteResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId) {
        try {
            foodCategoryRepository.deleteById(foodCategoryTargetId);

            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            throw  new EntityNotFoundException(FoodCategory.class, foodCategoryTargetId.toString());
        }
    }
}
