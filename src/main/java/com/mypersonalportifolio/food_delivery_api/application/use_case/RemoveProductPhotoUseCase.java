package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RemoveProductPhotoUseCase implements UseCase<UUID, Void> {

    private final ProductService productService;
    private final PhotoStorageService photoStorageService;

    public RemoveProductPhotoUseCase(ProductService productService,
                                     @Qualifier("S3StorageService") PhotoStorageService photoStorageService) {
        this.productService = productService;
        this.photoStorageService = photoStorageService;
    }

    @Transactional
    @Override
    public Void execute(UUID productId) {
        var productTarget = productService.findValidProduct(productId);

        var fileName = productTarget.getPhotoMetaData().getFileName();

        productTarget.setPhotoMetaData(null);

        photoStorageService.removePhoto(fileName);


        return null;
    }
}
