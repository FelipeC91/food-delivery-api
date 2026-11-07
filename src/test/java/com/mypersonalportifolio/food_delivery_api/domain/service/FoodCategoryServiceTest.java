package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodCategoryServiceTest {

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @InjectMocks
    private FoodCategoryService foodCategoryService;

    @Test
    void shouldSaveNewFoodCategoryWhenNameIsAvailable() {
        var candidate = new FoodCategory("Pizza", "Traditional pizza");
        when(foodCategoryRepository.existsByName(candidate.getName())).thenReturn(false);
        when(foodCategoryRepository.save(candidate)).thenReturn(candidate);

        var result = foodCategoryService.validateAndSave(candidate);

        assertSame(candidate, result);
        verify(foodCategoryRepository).existsByName(candidate.getName());
        verify(foodCategoryRepository).save(candidate);
    }
}
