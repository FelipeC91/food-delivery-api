package com.mypersonalportifolio.food_delivery_api.infrastructure.storage.local;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoToStoreDTO;
import com.mypersonalportifolio.food_delivery_api.application.storage.fileDetailsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component("localStorageService")
public class LocalPhotoStorageService implements PhotoStorageService {

    private final String LOCAL_PHOTO_STORAGE_BASE_PATH;

    public LocalPhotoStorageService(@Value("${application.storage.local.root-directory}") String localPhotoStorageBasePath) {
        LOCAL_PHOTO_STORAGE_BASE_PATH = localPhotoStorageBasePath;
    }

    @Override
    public void storePhoto(PhotoToStoreDTO newPhoto) {

        var fileAbsolutePath = Paths.get(LOCAL_PHOTO_STORAGE_BASE_PATH, newPhoto.getFileName());

        try {
            FileCopyUtils.copy(newPhoto.getFileInputStream(), Files.newOutputStream(fileAbsolutePath));

        } catch (IOException e) {
            throw new FailOnHandleFileException("Erro ao gravar arquivo", e);
        }
    }

    @Override
    public void removePhoto(String fileName) {
        var fileAbsolutePath = Paths.get(LOCAL_PHOTO_STORAGE_BASE_PATH, fileName);
        try {
            Files.deleteIfExists(fileAbsolutePath);
        } catch (IOException e) {
            throw new FailOnHandleFileException("Erro ao remover arquivo", e);
        }
    }

    @Override
    public fileDetailsDTO retrievePhoto(String fileName) {
        try {
            var fileInputStream = Files.newInputStream(Paths.get(LOCAL_PHOTO_STORAGE_BASE_PATH, fileName), StandardOpenOption.READ);

            return new fileDetailsDTO(fileInputStream, null);
        } catch (IOException e) {
            throw new FailOnHandleFileException("Erro ao recuperar arquivo", e);
        }
    }

    @Override
    public boolean exists(String fileName) {
        return Files.exists(Paths.get(LOCAL_PHOTO_STORAGE_BASE_PATH, fileName));
    }
}
