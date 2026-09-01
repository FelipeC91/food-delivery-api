package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public void mergeProperties(Map<String, Object> restaurantFieldsSourceProperties,
                                                Restaurant restaurantTarget) {
        if (restaurantFieldsSourceProperties == null || restaurantFieldsSourceProperties.isEmpty())
            throw new CandidateEntityInvalidException(Restaurant.class, null) ;


        var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

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


    public Restaurant updateProperties(Restaurant restaurantSource, Restaurant restaurantTarget) {
        BeanUtils.copyProperties(restaurantSource, restaurantTarget, "id");

        return restaurantRepository.save(restaurantTarget);
    }
}
