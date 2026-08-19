package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantService {

    public void mergeProperties(Map<String, Object> restaurantFieldsSourceProperties, Restaurant restaurantTarget) {
        if (restaurantFieldsSourceProperties == null || restaurantFieldsSourceProperties.isEmpty()) {
            throw new IllegalStateException("restaurantFieldsSourceProperties is null or empty") ;
        }

        var objectMapper = new ObjectMapper();
        var restaurantSource = objectMapper.convertValue(restaurantFieldsSourceProperties, Restaurant.class);

        BeanUtils.copyProperties(restaurantSource, restaurantTarget, filterNullPropertyNames(restaurantSource));
    }

    private String[] filterNullPropertyNames(Restaurant restaurant) {
        var beanWrapper = new BeanWrapperImpl(restaurant);

        var nullPropertiesList = new ArrayList<String>();

        Arrays.stream(beanWrapper.getPropertyDescriptors())
                        .forEach( propertyDescriptor  -> {
                            var propertyName = propertyDescriptor.getName();

                            if (beanWrapper.getPropertyValue(propertyName) == null)
                                nullPropertiesList.add(propertyName);
                        });

        return nullPropertiesList.toArray(new String[nullPropertiesList.size()]);

    }
}
