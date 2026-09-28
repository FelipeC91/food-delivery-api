package com.mypersonalportifolio.food_delivery_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductPhoto  {

    @Column(table = "product_photo", name = "file_name")
    private String fileName;

    @Column(table = "product_photo", name = "photo_description")
    private String description;

    @Column(table = "product_photo", name = "content_type")
    private String contentType;

    @Column(table = "product_photo", name = "file_size")
    private Long fileSize;

    public ProductPhoto(String fileName, String description, String contentType, Long fileSize) {
        this.fileName = fileName;
        this.description = description;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }
}

