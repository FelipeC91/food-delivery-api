package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityAlreadyExistsException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.FoodCategoryService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.FoodCategoryRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.FoodCategoryRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller.FoodCategoryControllerOpenApi;
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
public class FoodCategoryController implements FoodCategoryControllerOpenApi {

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
        var foodCategory =foodCategoryService.findVerifiedFoodCategory(foodCategoryId);

        return ResponseEntity.ok(assembler.toModel(foodCategory));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodCategoryRepresentationModel createResource(@RequestBody  @Valid FoodCategory foodCategoryCandidate) {
        return assembler.toModel( foodCategoryService.validateAndSave(foodCategoryCandidate) );
    }

    @PutMapping("/{foodCategoryId}")
    public ResponseEntity<?> updateResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId,
                                                @RequestBody FoodCategory foodCategorySource) {
        var foodCategoryTarget = foodCategoryService.findVerifiedFoodCategory(foodCategoryTargetId);

        var updatedFoodCategory=  foodCategoryService.updateProperties(foodCategoryTarget, foodCategorySource);

        return ResponseEntity.status(HttpStatus.OK).body(assembler.toModel(updatedFoodCategory));

    }

    @DeleteMapping("/{foodCategoryId}")
    public ResponseEntity<?> deleteResource(@PathVariable("foodCategoryId") UUID foodCategoryTargetId) {
        foodCategoryService.delete( foodCategoryTargetId );
        return ResponseEntity.noContent().build();
    }
}
