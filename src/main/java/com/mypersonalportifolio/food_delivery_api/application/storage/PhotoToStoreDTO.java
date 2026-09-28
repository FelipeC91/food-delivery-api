package com.mypersonalportifolio.food_delivery_api.application.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

@Getter
@Builder
public class PhotoToStoreDTO {
        final String fileName;
        final String fileContentType;
        final InputStream fileInputStream;
}
