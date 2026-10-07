package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.FoodCategoryService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.FoodCategoryRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.FoodCategoryRepresentationModel;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/food-categories")
public class FoodCategoryController {

    private final FoodCategoryRepository foodCategoryRepository;

    private final FoodCategoryService foodCategoryService;

    private final FoodCategoryRepresentationModelAssembler assembler;

    private final PagedResourcesAssembler<FoodCategory> pagedResourcesAssembler;

    public FoodCategoryController(FoodCategoryRepository foodCategoryRepository,
                                  FoodCategoryService foodCategoryService,
                                  FoodCategoryRepresentationModelAssembler assembler,
                                  PagedResourcesAssembler<FoodCategory> pagedResourcesAssembler) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.foodCategoryService = foodCategoryService;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public PagedModel<FoodCategoryRepresentationModel> listAllResources(@PageableDefault(size = 10) Pageable pageable) {
            var foodCategoryPage = foodCategoryRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(foodCategoryPage, assembler);
    }

    @GetMapping("/{foodCategoryId}")
    public ResponseEntity<FoodCategoryRepresentationModel> findSingleResource(@PathVariable UUID foodCategoryId) {
        var foodCategory =foodCategoryService.findValidFoodCategory(foodCategoryId);

        return ResponseEntity.ok(assembler.toModel(foodCategory));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodCategoryRepresentationModel createResource(@RequestBody  @Valid FoodCategory foodCategoryCandidate) {
        try {
            var savedFoodCategory = foodCategoryRepository.save(foodCategoryCandidate);

            return assembler.toModel(savedFoodCategory);

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(FoodCategory.class, foodCategoryCandidate.getName());

        }
    }

    @PutMapping("/{foodCategoryId}")
    public ResponseEntity<?> updateResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId,
                                                @RequestBody FoodCategory foodCategorySource) {
        var foodCategoryTarget = foodCategoryService.findValidFoodCategory(foodCategoryTargetId);

        var updatedFoodCategory=  foodCategoryService.updateProperties(foodCategoryTarget, foodCategorySource);

        return ResponseEntity.status(HttpStatus.OK).body(assembler.toModel(updatedFoodCategory));

    }

    @DeleteMapping("/{foodCategoryId}")
    public ResponseEntity<?> deleteResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId) {
        try {
            foodCategoryRepository.deleteById(foodCategoryTargetId);

            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            throw  new EntityNotFoundException(FoodCategory.class, foodCategoryTargetId.toString());

        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(FoodCategory.class, foodCategoryTargetId.toString());
        }
    }
}
