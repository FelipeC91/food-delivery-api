package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.model.ProductPhoto;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RemoveProductPhotoUseCaseTest {

    @Test
    void clearsProductPhotoAndRemovesStoredFile() {
        var productService = mock(ProductService.class);
        var photoStorageService = mock(PhotoStorageService.class);
        var useCase = new RemoveProductPhotoUseCase(productService, photoStorageService);
        var productId = UUID.randomUUID();
        var product = mock(Product.class);
        var photo = new ProductPhoto("photo.jpg", "Photo", "image/jpeg", 12L);
        when(productService.findValidProduct(productId)).thenReturn(product);
        when(product.getPhotoMetaData()).thenReturn(photo);

        useCase.execute(productId);

        verify(product).setPhotoMetaData(null);
        verify(photoStorageService).removePhoto("photo.jpg");
    }
}
