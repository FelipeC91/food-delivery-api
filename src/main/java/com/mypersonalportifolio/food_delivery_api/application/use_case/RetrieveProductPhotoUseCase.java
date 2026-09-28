package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.ProductPhotoOutputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct.ProductPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class RetrieveProductPhotoUseCase implements UseCase<UUID, ProductPhotoOutputDTO> {

    private final ProductService productService;
    private final PhotoStorageService photoStorageService;
    private final ProductPhotoMapper productPhotoMapper;

    @Autowired
    public RetrieveProductPhotoUseCase(ProductService productService,
                                       @Qualifier("S3StorageService") PhotoStorageService photoStorageService,
                                       ProductPhotoMapper productPhotoMapper) {
        this.productService = productService;
        this.photoStorageService = photoStorageService;
        this.productPhotoMapper = productPhotoMapper;
    }

    @Override
    public ProductPhotoOutputDTO execute(UUID productId) {
        var productTarget = productService.findValidProduct(productId);

        if (Objects.isNull(productTarget.getPhotoMetaData()))
            throw new IllegalStateException();

        var photoDataOutputDTO = productPhotoMapper.toOutputDTO(productTarget.getPhotoMetaData());

        photoDataOutputDTO.setFileDetails(photoStorageService.retrievePhoto(photoDataOutputDTO.getFileName()));

        return photoDataOutputDTO;

    }
}
