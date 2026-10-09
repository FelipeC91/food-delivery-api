package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto;

import com.mypersonalportifolio.food_delivery_api.application.storage.fileDetailsDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@RequiredArgsConstructor
public class ProductPhotoMetaDataRepresentationModel extends RepresentationModel<ProductPhotoMetaDataRepresentationModel> {
    private final String fileName;

    private final String description;

    private final String contentType;

    private final Long fileSize;

    @Setter
    private fileDetailsDTO fileDetails;

}
