package com.mypersonalportifolio.food_delivery_api.application.storage;

import java.io.IOException;

public interface PhotoStorageService {

    void storePhoto(PhotoToStoreDTO newPhoto) throws IOException;

    void removePhoto(String fileName);

    fileDetailsDTO retrievePhoto(String fileName);

    boolean exists(String fileName);


}
