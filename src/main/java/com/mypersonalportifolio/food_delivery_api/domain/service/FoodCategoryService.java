package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    public FoodCategory updateProperties(FoodCategory foodCategoryTarget, FoodCategory foodCategorySource) {

        BeanUtils.copyProperties(foodCategorySource,foodCategoryTarget, "id");


        return foodCategoryRepository.saveAndFlush(foodCategoryTarget);
    }
}
