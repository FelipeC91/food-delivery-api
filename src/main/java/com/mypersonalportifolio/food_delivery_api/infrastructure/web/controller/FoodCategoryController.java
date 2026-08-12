package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @GetMapping
    public List<FoodCategory> listAllResources() {
        return foodCategoryRepository.findAll();
    }

    @GetMapping("/{foodCategoryId}")
    public ResponseEntity<?> findOneResource(@PathVariable UUID foodCategoryId) {
        var foodCategoryOptional = foodCategoryRepository.findById(foodCategoryId);

        if (foodCategoryOptional.isPresent())
            return ResponseEntity.ok(foodCategoryOptional.get());

        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createResource(@RequestBody FoodCategory foodCategory) {
        var foodCategoryOptional = foodCategoryRepository.findByName(foodCategory.getName());

        if (foodCategoryOptional.isEmpty()) {
            var newFoodCategory = foodCategoryRepository.save(foodCategory);

            return ResponseEntity.ok(newFoodCategory);
        } else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Categoria já catalogada");
    }

    @PutMapping("/{foodCategoryId}")
    public ResponseEntity<?> updateResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId,
                                                @RequestBody FoodCategory foodCategorySource) {
        var foodCategoryTargetOptional = foodCategoryRepository.findById(foodCategoryTargetId);

        if (foodCategoryTargetOptional.isPresent()){
            BeanUtils.copyProperties(foodCategoryTargetOptional.get(), foodCategorySource, "id");

            var foodCategoryUpdated = foodCategoryRepository.saveAndFlush(foodCategoryTargetOptional.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(foodCategoryUpdated);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{foodCategoryId}")
    public ResponseEntity<?> deleteResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId) {
        try {
            foodCategoryRepository.deleteById(foodCategoryTargetId);

            return ResponseEntity.noContent().build();

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
