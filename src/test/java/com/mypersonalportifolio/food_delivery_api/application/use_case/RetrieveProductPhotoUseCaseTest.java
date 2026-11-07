package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.storage.FileDetailsDTO;
import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.model.ProductPhoto;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.ProductPhotoMetaDataRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.ProductPhotoMapper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RetrieveProductPhotoUseCaseTest {

    @Test
    void returnsPhotoMetadataWithRetrievedFileDetails() {
        var productService = mock(ProductService.class);
        var storageService = mock(PhotoStorageService.class);
        var mapper = mock(ProductPhotoMapper.class);
        var useCase = new RetrieveProductPhotoUseCase(productService, storageService, mapper);
        var productId = UUID.randomUUID();
        var product = mock(Product.class);
        var photo = new ProductPhoto("photo.jpg", "Photo", "image/jpeg", 12L);
        var representation = new ProductPhotoMetaDataRepresentationModel("photo.jpg", "Photo", "image/jpeg", 12L);
        var fileDetails = new FileDetailsDTO(new ByteArrayInputStream(new byte[0]), null);
        when(productService.findValidProduct(productId)).thenReturn(product);
        when(product.getPhotoMetaData()).thenReturn(photo);
        when(mapper.toOutputDTO(photo)).thenReturn(representation);
        when(storageService.retrievePhoto("photo.jpg")).thenReturn(fileDetails);

        var result = useCase.execute(productId);

        assertSame(representation, result);
        assertSame(fileDetails, result.getFileDetails());
        verify(storageService).retrievePhoto("photo.jpg");
    }

    @Test
    void rejectsProductsWithoutPhotoMetadata() {
        var productService = mock(ProductService.class);
        var product = mock(Product.class);
        var productId = UUID.randomUUID();
        when(productService.findValidProduct(productId)).thenReturn(product);
        when(product.getPhotoMetaData()).thenReturn(null);
        var useCase = new RetrieveProductPhotoUseCase(productService, mock(PhotoStorageService.class),
                mock(ProductPhotoMapper.class));

        assertThrows(IllegalStateException.class, () -> useCase.execute(productId));
    }
}
