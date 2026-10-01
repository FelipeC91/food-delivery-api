package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.FoodCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodCategoryControllerTest {

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @Mock
    private FoodCategoryService foodCategoryService;

    @InjectMocks
    private FoodCategoryController foodCategoryController;

    private FoodCategory foodCategory;

    @BeforeEach
    void setUp() {
        foodCategory = new FoodCategory("Pizza", "Traditional pizza");
    }


    @Test
    void shouldThrowWhenFoodCategoryIsNotFound() {
        var id = UUID.randomUUID();
        when(foodCategoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> foodCategoryController.findOneResource(id));
    }

    @Test
    void shouldTranslateInvalidCandidateExceptionWhenCreating() {
        when(foodCategoryRepository.save(foodCategory)).thenThrow(new IllegalArgumentException());

        assertThrows(CandidateEntityInvalidException.class,
                () -> foodCategoryController.createResource(foodCategory));
    }

    @Test
    void shouldUpdateFoodCategory() {
        var id = UUID.randomUUID();
        var source = new FoodCategory("Updated pizza", "Updated description");
        var updated = new FoodCategory("Updated pizza", "Updated description");
        when(foodCategoryRepository.findById(id)).thenReturn(Optional.of(foodCategory));
        when(foodCategoryService.updateProperties(foodCategory, source)).thenReturn(updated);

        var result = foodCategoryController.updateResource(id, source);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertSame(updated, result.getBody());
        verify(foodCategoryService).updateProperties(foodCategory, source);
    }

    @Test
    void shouldDeleteFoodCategory() {
        var id = UUID.randomUUID();

        var result = foodCategoryController.deleteResource(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(foodCategoryRepository).deleteById(id);
    }

    @Test
    void shouldTranslateIllegalArgumentExceptionWhenDeleting() {
        var id = UUID.randomUUID();
        doThrow(new IllegalArgumentException()).when(foodCategoryRepository).deleteById(id);

        assertThrows(EntityNotFoundException.class,
                () -> foodCategoryController.deleteResource(id));
    }


    @Test
    void shouldTranslateDataIntegrityViolationWhenDeleting() {
        var id = UUID.randomUUID();
        doThrow(new DataIntegrityViolationException("category is in use"))
                .when(foodCategoryRepository).deleteById(id);

        assertThrows(EntityIntegrityViolationException.class,
                () -> foodCategoryController.deleteResource(id));
    }
}
