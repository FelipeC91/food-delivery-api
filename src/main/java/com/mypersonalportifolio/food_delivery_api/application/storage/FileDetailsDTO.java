package com.mypersonalportifolio.food_delivery_api.application.storage;

import lombok.Getter;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Getter
public class FileDetailsDTO {
    private Optional<InputStream> fileInputStream;
    private Optional<URL> fileS3Location;

    public FileDetailsDTO(InputStream fileInputStream, URL fileS3Location) {
        this.fileInputStream = Optional.ofNullable(fileInputStream);
        this.fileS3Location = Optional.ofNullable(fileS3Location);
    }
}
