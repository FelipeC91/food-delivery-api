package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.RestaurantInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.exception.CandidateEntityInvalidException;
import com.mypersonalportifolio.food_delivery_api.domain.model.Restaurant;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.RestaurantService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.RestaurantSimplifiedRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.RestaurantRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.RestaurantSimplifiedRepresentationModelAssembler;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.RestaurantMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RequestMapping("/restaurants")
@RestController
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepresentationModelAssembler assembler;
    private final RestaurantSimplifiedRepresentationModelAssembler simplifiedAssembler;
    private final PagedResourcesAssembler<Restaurant> pagedResourcesAssembler;


    public RestaurantController(RestaurantRepository restaurantRepository,
                                RestaurantService restaurantService,
                                RestaurantMapper restaurantMapper,
                                RestaurantRepresentationModelAssembler assembler,
                                RestaurantSimplifiedRepresentationModelAssembler simplifiedAssembler, PagedResourcesAssembler<Restaurant> pagedResourcesAssembler) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
        this.assembler = assembler;
        this.simplifiedAssembler = simplifiedAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public PagedModel<RestaurantSimplifiedRepresentationModel> listAllResources(@PageableDefault(size = 8) Pageable pageable) {
        var page = restaurantRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(page, simplifiedAssembler);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantRepresentationModel> findSingleResource(@PathVariable UUID restaurantId) {
        var restaurantTarget = restaurantService.findValidRestaurant(restaurantId);

        return ResponseEntity.ok(assembler.toModel(restaurantTarget));
    }

    @PostMapping
    private ResponseEntity<RestaurantRepresentationModel> createResource(@RequestBody @Valid RestaurantInputDTO restaurantInputDTO) {
        try {
            var restaurantCandidate = restaurantMapper.dtoToRestaurant(restaurantInputDTO);

            var validRestaurant = restaurantService.validateNewRestaurant(restaurantCandidate);
            var savedRestaurant = restaurantRepository.save(validRestaurant);

            return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedRestaurant));

        } catch (IllegalArgumentException e) {
            throw new CandidateEntityInvalidException(Restaurant.class, restaurantInputDTO.name());

        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> updateResource(@PathVariable("restaurantId") UUID restaurantTargetId,
                                            @RequestBody @Valid RestaurantInputDTO restaurantInput) {
        var restaurantTarget = restaurantService.findValidRestaurant(restaurantTargetId);

        var restaurantSource = restaurantMapper.dtoToRestaurant(restaurantInput);

        var savedRestaurant = restaurantService.updateProperties(restaurantSource, restaurantTarget);

        return ResponseEntity.ok(assembler.toModel(savedRestaurant));
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


}
