package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.FoodCategoryService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.FoodCategoryRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.FoodCategoryRepresentationModelAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodCategoryControllerTest {

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @Mock
    private FoodCategoryService foodCategoryService;

    @Mock
    private FoodCategoryRepresentationModelAssembler assembler;

    @Mock
    private PagedResourcesAssembler<FoodCategory> pagedResourcesAssembler;

    @Mock
    private FoodCategoryRepresentationModel representationModel;

    private FoodCategoryController foodCategoryController;

    private FoodCategory foodCategory;

    @BeforeEach
    void setUp() {
        foodCategory = new FoodCategory("Pizza", "Traditional pizza");
        foodCategoryController = new FoodCategoryController(foodCategoryRepository, foodCategoryService,
                assembler, pagedResourcesAssembler);
    }


    @Test
    void shouldThrowWhenFoodCategoryIsNotFound() {
        var id = UUID.randomUUID();
        when(foodCategoryService.findVerifiedFoodCategory(id))
                .thenThrow(new EntityNotFoundException(FoodCategory.class, id.toString()));

        assertThrows(EntityNotFoundException.class,
                () -> foodCategoryController.findSingleResource(id));
        verify(foodCategoryService).findVerifiedFoodCategory(id);
    }

    @Test
    void shouldTranslateInvalidCandidateExceptionWhenCreating() {
        when(foodCategoryService.validateAndSave(foodCategory))
                .thenThrow(new CandidateEntityInvalidException(FoodCategory.class, foodCategory.getName()));

        assertThrows(CandidateEntityInvalidException.class,
                () -> foodCategoryController.createResource(foodCategory));
        verify(foodCategoryService).validateAndSave(foodCategory);
    }

    @Test
    void shouldUpdateFoodCategory() {
        var id = UUID.randomUUID();
        var source = new FoodCategory("Updated pizza", "Updated description");
        var updated = new FoodCategory("Updated pizza", "Updated description");
        when(foodCategoryService.findVerifiedFoodCategory(id)).thenReturn(foodCategory);
        when(foodCategoryService.updateProperties(foodCategory, source)).thenReturn(updated);
        when(assembler.toModel(updated)).thenReturn(representationModel);

        var result = foodCategoryController.updateResource(id, source);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(representationModel, result.getBody());
        verify(foodCategoryService).findVerifiedFoodCategory(id);
        verify(foodCategoryService).updateProperties(foodCategory, source);
    }

    @Test
    void shouldDeleteFoodCategory() {
        var id = UUID.randomUUID();

        var result = foodCategoryController.deleteResource(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(foodCategoryService).delete(id);
    }

    @Test
    void shouldPropagateNotFoundExceptionWhenDeleting() {
        var id = UUID.randomUUID();
        doThrow(new EntityNotFoundException(FoodCategory.class, id.toString()))
                .when(foodCategoryService).delete(id);

        assertThrows(EntityNotFoundException.class,
                () -> foodCategoryController.deleteResource(id));
    }


    @Test
    void shouldPropagateIntegrityViolationWhenDeleting() {
        var id = UUID.randomUUID();
        doThrow(new EntityIntegrityViolationException(FoodCategory.class, id.toString()))
                .when(foodCategoryService).delete(id);

        assertThrows(EntityIntegrityViolationException.class,
                () -> foodCategoryController.deleteResource(id));
    }
}
