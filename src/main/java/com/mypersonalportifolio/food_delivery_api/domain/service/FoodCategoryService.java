package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityAlreadyExistsException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityIntegrityViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.FoodCategory;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }

    @Transactional
    public FoodCategory updateProperties(FoodCategory foodCategoryTarget, FoodCategory foodCategorySource) {
        if (foodCategoryRepository.existsByName(foodCategoryTarget.getName()))
            throw new EntityAlreadyExistsException(foodCategoryTarget.getClass(), "FoodCategory with name " + foodCategoryTarget.getName() + " already exists");

        BeanUtils.copyProperties(foodCategorySource,foodCategoryTarget, "id");


        return foodCategoryRepository.saveAndFlush(foodCategoryTarget);
    }

    public FoodCategory findVerifiedFoodCategory(UUID foodCategoryTargetId) {
        return foodCategoryRepository.findById(foodCategoryTargetId)
                .orElseThrow( () -> new EntityNotFoundException(FoodCategory.class,foodCategoryTargetId.toString() ));

    }

    public FoodCategory validateAndSave(@Valid FoodCategory foodCategoryCandidate) {
        try {

            if (foodCategoryRepository.existsByName(foodCategoryCandidate.getName()))
                throw new EntityAlreadyExistsException(foodCategoryCandidate.getClass(), "FoodCategory with name " + foodCategoryCandidate.getName() + " already exists");

            var savedFoodCategory = foodCategoryRepository.save(foodCategoryCandidate);

            log.info("FoodCategory with name " + foodCategoryCandidate.getName() + " saved successfully");

            return savedFoodCategory;

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(FoodCategory.class, foodCategoryCandidate.getName());

        }
    }

    public void delete(UUID foodCategoryTargetId) {
        try {
            foodCategoryRepository.deleteById(foodCategoryTargetId);

            log.info("FoodCategory with id " + foodCategoryTargetId + " deleted successfully");
        } catch (IllegalArgumentException e) {
            throw  new EntityNotFoundException(FoodCategory.class, foodCategoryTargetId.toString());

        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(FoodCategory.class, foodCategoryTargetId.toString());
        }
    }
}
