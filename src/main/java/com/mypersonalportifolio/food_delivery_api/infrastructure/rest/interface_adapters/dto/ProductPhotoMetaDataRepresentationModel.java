package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto;

import com.mypersonalportifolio.food_delivery_api.application.storage.fileDetailsDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "ProductPhotoMetaData")
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
