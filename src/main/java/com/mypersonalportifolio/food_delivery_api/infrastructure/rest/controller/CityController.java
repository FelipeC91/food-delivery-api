package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;


import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.CityInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.City;
import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.domain.repository.CityRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.CityService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.CityRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.CityRepresentationModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cities", description = "Endpoints to manage cities' registration - this resource is associated with the Addresses resource")

@RequestMapping("/cities")
@RestController
public class CityController {

    private final CityRepository cityRepository;
    private final CityService cityService;
    private final CityRepresentationModelAssembler assembler;
    private final PagedResourcesAssembler<City> pagedResourcesAssembler;

    public CityController(CityRepository cityRepository,
                          CityService cityService,
                          CityRepresentationModelAssembler assembler, PagedResourcesAssembler<City> pagedResourcesAssembler) {
        this.cityRepository = cityRepository;
        this.cityService = cityService;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Operation(summary = "Get all city registration")
    @GetMapping
    public PagedModel<CityRepresentationModel> listAllResources(@PageableDefault(size = 10) Pageable pageable) {
//        return cityRepository.findAll().stream()
//                            .map(cityMapper::toCityOutputDTO)
//                            .peek(this::assembleHateoasLinks)
//                            .collect( Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of) );

        return pagedResourcesAssembler.toModel( cityRepository.findAll(pageable),  assembler);
    }

    @Operation(summary = "Get a city by Id")
    @GetMapping("/{cityId}")
    public ResponseEntity<CityRepresentationModel> findOneResource(@Parameter(description = "a city Id", required = true, example = "1")
                                                    @PathVariable Long cityId) {
        var city = cityService.findValidCity(cityId);

        return ResponseEntity.ok(assembler.toModel(city));
    }

    @Operation(summary = "Register a city")
    @PostMapping
    public ResponseEntity<?> createResource(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,description = "A valid representation of a city")
                                                @RequestBody @Valid CityInputDTO cityCandidate){
            var city = cityService.create(cityCandidate);

            return ResponseEntity.status(HttpStatus.CREATED)
                                    .body( assembler.toModel(city) );

    }

    @Operation(summary = "Update a city register")
    @PutMapping("/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public CityRepresentationModel updateResource(@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "A city representation containing its name and stateId")
                                   @Parameter(description = "an existent city Id", required = true, example = "1") @PathVariable Long cityId,
                                                  @RequestBody @Valid CityInputDTO citySource) {
        var cityTarget = cityService.findValidCity(cityId);

        var updatedCity = cityService.updateProperties(citySource, cityTarget);

        return assembler.toModel(updatedCity);
    }

    @Operation(summary = "Delete a city register")
    @DeleteMapping("/cityId")
    public ResponseEntity<City> deleteResource(@Parameter(description = "an existent city Id", required = true, example = "1")
                                                   @PathVariable Long cityId) {
        cityRepository.deleteById(cityId);

        return ResponseEntity.noContent().build();
    }

}
