package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.RestaurantInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.RestaurantOutputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.repository.FoodCategoryRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct.RestaurantMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    RestaurantMapper restaurantMapper;

    @GetMapping
    public Page<RestaurantOutputDTO> listAllResources(Pageable pageable) {
        return restaurantRepository.findAllReturningBasicInfo(pageable);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantOutputDTO> findResource(@PathVariable UUID restaurantId) {
        var restaurantTarget = restaurantRepository.findById(restaurantId)
                .orElseThrow( () -> new EntityNotFoundException(Restaurant.class, restaurantId.toString()));

        var restaurantDTO = restaurantMapper.restaurantToOutputDTO(restaurantTarget);
        return ResponseEntity.ok(restaurantDTO);

    }

    @PostMapping
    private ResponseEntity<?> createResource(@RequestBody @Valid RestaurantInputDTO restaurantInputDTO) {
        try {
            var restaurantCandidate = restaurantMapper.dtoToRestaurant(restaurantInputDTO);

            var validRestaurant = restaurantService.validateNewRestaurant(restaurantCandidate);
            var savedRestaurant = restaurantRepository.save(validRestaurant);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(Restaurant.class, restaurantInputDTO.name());

        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> updateResource(@PathVariable("restaurantId") UUID restaurantTargetId,
                                            @RequestBody @Valid RestaurantInputDTO restaurantInputDTOSource) {
        var restaurantTarget = restaurantRepository.findById(restaurantTargetId)
                .orElseThrow( () -> new EntityNotFoundException(Restaurant.class, restaurantTargetId.toString()));

        var restaurantSource = restaurantMapper.dtoToRestaurant(restaurantInputDTOSource);

        var savedRestaurant = restaurantService.updateProperties(restaurantSource, restaurantTarget);

        return ResponseEntity.ok(restaurantMapper.restaurantToOutputDTO(savedRestaurant));
    }

//    @PatchMapping("/{restaurantId}")
//    public ResponseEntity<?> updatePartiallyResource(@PathVariable("restaurantId") UUID restaurantTargetId,
//                                                        @RequestBody Map<String, Object> restaurantFieldsSourceProperties,
//                                                        HttpInputMessage inputMessage) {
//        var restaurantTarget = restaurantRepository.findById(restaurantTargetId)
//                                                        .orElseThrow(()  -> new EntityNotFoundException(Restaurant.class, restaurantTargetId.toString() ));
//
//        try {
//            restaurantService.mergeProperties(restaurantFieldsSourceProperties, restaurantTarget);
//
//        } catch (IllegalArgumentException e) {
//            var rootCause = ExceptionUtils.getRootCause(e);
//
//            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, inputMessage);
//        }
//
//
//        return updateResource(restaurantTargetId, restaurantTarget);
//    }

    @PutMapping("/{restaurantTargetId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateResource(@PathVariable UUID restaurantTargetId) {
        var restaurantTarget = restaurantRepository.findById(restaurantTargetId)
                .orElseThrow(()  -> new EntityNotFoundException(Restaurant.class, restaurantTargetId.toString() ));

        restaurantService.activate(restaurantTarget);
    }

    @PutMapping("/{restaurantTargetId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateResource(@PathVariable UUID restaurantTargetId) {
        var restaurantTarget = restaurantRepository.findById(restaurantTargetId)
                .orElseThrow(()  -> new EntityNotFoundException(Restaurant.class, restaurantTargetId.toString() ));

        restaurantService.deactivate(restaurantTarget);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/active-batch")
    public void activateResourceBatch(@RequestBody List<UUID> restaurantsTargetIdBatch) {
        restaurantService.activateAll(restaurantsTargetIdBatch);
    }

    @DeleteMapping("/inactive-batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateResourceBatch(@RequestBody List<UUID> restaurantsTargetIdBatch) {
        restaurantService.deactivateAll(restaurantsTargetIdBatch);
    }
}
