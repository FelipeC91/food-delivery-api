package com.mypersonalportifolio.food_delivery_api.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mypersonalportifolio.food_delivery_api.domain.exception.*;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.PaymentMethod;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.PaymentMethodRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.UserRepository;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import java.util.*;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void mergeProperties(Map<String, Object> restaurantFieldsSourceProperties,
                                                Restaurant restaurantTarget)  {
        if (restaurantFieldsSourceProperties == null || restaurantFieldsSourceProperties.isEmpty())
            throw new CandidateEntityInvalidException(Restaurant.class, null) ;


        var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        var restaurantSource = objectMapper.convertValue(restaurantFieldsSourceProperties, Restaurant.class);

        BeanUtils.copyProperties(restaurantSource, restaurantTarget, filterNullPropertyNames(restaurantSource));

        var entityName = "restaurant";
        var bindingResult = new BeanPropertyBindingResult(restaurantTarget, entityName);

        validator.validate(restaurantTarget, bindingResult);

        if (bindingResult.hasErrors())
            throw new FailOnValidateEntityPropertiesException(bindingResult, entityName);

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

    @Transactional
    public Restaurant updateProperties(Restaurant restaurantSource, Restaurant restaurantTarget) {

        restaurantSource = validateCity(restaurantSource);

        BeanUtils.copyProperties(restaurantSource, restaurantTarget, "id");

        return restaurantRepository.save(restaurantTarget);
    }

    private Restaurant validateCity(Restaurant restaurantSource) {
        if (Objects.isNull( restaurantSource.getAddress() ) && restaurantSource.getAddress().getStreetName().isEmpty()) {
            throw new EntityNotFoundException(City.class, null);
        }
        var cityName = restaurantSource.getAddress().getCity().getName();

        var city = cityRepository.findByName(cityName)
                .orElseThrow(() -> new EntityNotFoundException(City.class, cityName));

        restaurantSource.getAddress().setCity(city);

        return restaurantSource;
    }

    @Transactional
    public void activate(Restaurant validRestaurant) {
        validRestaurant.setActive(true);

        if ( !validRestaurant.isActive()  )
            throw new RestaurantPendingRegistrationException();
    }


    @Transactional
    public void deactivate(Restaurant validRestaurant) {
        validRestaurant.setActive(false);
    }
    @Transactional
    public void activateAll(List<UUID> restaurantIdBatch) {
        restaurantRepository.findAllById(restaurantIdBatch)
                .forEach(this::activate);
    }

    @Transactional
    public void deactivateAll(List<UUID> restaurantIdBatch) {
        restaurantRepository.findAllById(restaurantIdBatch)
                .forEach(this::deactivate);
    }

    public Restaurant validateNewRestaurant(Restaurant restaurantCandidate) {
        return  validateCity(restaurantCandidate);
    }

    //handle Address


    //handle PaymentMethods
    @Transactional
    public void handleDelete(UUID restaurantTargetId, UUID paymentMethodId) {
        var restaurantTarget = findValidrestaurant(restaurantTargetId);

        var validPaymentMethod =paymentMethodRepository.findById(paymentMethodId)
                        .orElseThrow(() -> new EntityNotFoundException(PaymentMethod.class, paymentMethodId.toString()));

        restaurantTarget.detachAllowedPaymentMethod(validPaymentMethod);
    }

    @Transactional
    public void addPaymentMethod(UUID restaurantTargetId, UUID paymentMethodId) {
        var restaurantTarget = findValidrestaurant(restaurantTargetId);

        var paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(()-> new EntityNotFoundException(PaymentMethod.class, paymentMethodId.toString()));


        var isAlreadyAttached = ! restaurantTarget.attachAllowedPaymentMethod(paymentMethod);

        if (isAlreadyAttached)
            throw new BusinessConstraintsViolationException("Forma de pagamento já consta associada ");

        restaurantRepository.flush();
    }

    public Restaurant findValidrestaurant(UUID restaurantTargetId) {
        return restaurantRepository.findById(restaurantTargetId)
                .orElseThrow(()-> new EntityNotFoundException(Restaurant.class, restaurantTargetId.toString()));
    }

    //handle Users
    @Transactional
    public void addManager(Restaurant restaurantTarget, UUID userId) {
        var validUser = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(User.class, userId.toString()));

        var isAlreadyAdded  = !restaurantTarget.addManager(validUser);

        if (isAlreadyAdded)
            throw new BusinessConstraintsViolationException("Forma de pagamento já consta associada");
    }

    @Transactional
    public void removeManager(Restaurant restaurantTarget, UUID paymentMethodId) {
        var validUser = userRepository.findById(paymentMethodId)
                .orElseThrow(()-> new EntityNotFoundException(User.class, paymentMethodId.toString()));

        var isAlreadyAdded  =restaurantTarget.removeManager(validUser);

        if (isAlreadyAdded)
            throw new BusinessConstraintsViolationException("Forma de pagamento já consta associada");
    }
}
