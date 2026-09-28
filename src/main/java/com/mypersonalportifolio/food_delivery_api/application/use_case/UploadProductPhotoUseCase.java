package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoToStoreDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.storage.local.FailOnHandleFileException;
import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.domain.model.ProductPhoto;
import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.ProductPhotoInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.mapstruct.ProductPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@Service
public class UploadProductPhotoUseCase implements UseCase<ProductPhotoInputDTO, ProductPhoto> {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final PhotoStorageService photoStorageService;
    private final ProductPhotoMapper productPhotoMapper;


    @Autowired
    public UploadProductPhotoUseCase(ProductService productService, ProductRepository productRepository,
                                     PhotoStorageService photoStorageService,
                                     ProductPhotoMapper productPhotoMapper) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.photoStorageService = photoStorageService;
        this.productPhotoMapper = productPhotoMapper;
    }

    @Transactional
    @Override
    public ProductPhoto execute(ProductPhotoInputDTO productPhotoInput) {
        var photoCandidate = this.productPhotoMapper.toProductPhoto(productPhotoInput); //this.assembleProductPhoto(productPhotoInput);

        var productTarget= productService.findValidProduct(productPhotoInput.getProductId());

        if (Objects.nonNull( productTarget.getPhotoMetaData() )) {

            var oldPhotoName = productTarget.getPhotoMetaData().getFileName();

            photoStorageService.removePhoto(oldPhotoName);
        }

        productTarget.setPhotoMetaData(photoCandidate);

        var savedProductPhotoPhoto = productRepository.saveAndFlush(productTarget);


        try {
            var toStore = PhotoToStoreDTO.builder()
                                            .fileName(photoCandidate.getFileName())
                                            .fileContentType(productPhotoInput.getFile().getContentType())
                                            .fileInputStream(productPhotoInput.getFile().getInputStream())
                                            .build();

            photoStorageService.storePhoto(toStore);


            return savedProductPhotoPhoto.getPhotoMetaData();
        } catch (IOException e) {
            throw new FailOnHandleFileException("Erro ao receber arquivo para gravação", e);
        }

    }

//    private ProductPhoto assembleProductPhoto(ProductPhotoInputDTO productPhotoInputDTO) {
//        return new ProductPhoto(
//                (UUID.randomUUID().toString() + "_" + productPhotoInputDTO.getFile().getOriginalFilename()),
//                productPhotoInputDTO.getDescription(),
//                productPhotoInputDTO.getFile().getContentType(),
//                productPhotoInputDTO.getFile().getSize()
//        );
//    }
}
