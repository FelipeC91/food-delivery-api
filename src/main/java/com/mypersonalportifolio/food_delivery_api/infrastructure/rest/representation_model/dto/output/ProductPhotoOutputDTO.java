package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.output;

import com.mypersonalportifolio.food_delivery_api.application.storage.fileDetailsDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ProductPhotoOutputDTO {
    private final String fileName;

    private final String description;

    private final String contentType;

    private final Long fileSize;

    @Setter
    private fileDetailsDTO fileDetails;

}
