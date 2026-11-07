package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.ProductPhotoInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.Product;
import com.mypersonalportifolio.food_delivery_api.domain.model.ProductPhoto;
import com.mypersonalportifolio.food_delivery_api.domain.repository.ProductRepository;
import com.mypersonalportifolio.food_delivery_api.domain.service.ProductService;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.ProductPhotoMapper;
import com.mypersonalportifolio.food_delivery_api.infrastructure.storage.local.FailOnHandleFileException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UploadProductPhotoUseCaseTest {

    @Test
    void replacesExistingPhotoAndStoresNewFile() throws Exception {
        var productService = mock(ProductService.class);
        var productRepository = mock(ProductRepository.class);
        var storageService = mock(PhotoStorageService.class);
        var mapper = mock(ProductPhotoMapper.class);
        var useCase = new UploadProductPhotoUseCase(productService, productRepository, storageService, mapper);
        var productId = UUID.randomUUID();
        var input = new ProductPhotoInputDTO("Dish photo",
                new MockMultipartFile("file", "dish.jpg", "image/jpeg", new byte[]{1, 2}));
        input.setProductId(productId);
        var product = mock(Product.class);
        var oldPhoto = new ProductPhoto("old.jpg", "Old", "image/jpeg", 1L);
        var newPhoto = new ProductPhoto("new.jpg", "Dish photo", "image/jpeg", 2L);
        when(mapper.toProductPhoto(input)).thenReturn(newPhoto);
        when(productService.findValidProduct(productId)).thenReturn(product);
        when(product.getPhotoMetaData()).thenReturn(oldPhoto, oldPhoto, newPhoto);
        when(productRepository.saveAndFlush(product)).thenReturn(product);

        var result = useCase.execute(input);

        assertSame(newPhoto, result);
        verify(storageService).removePhoto("old.jpg");
        verify(product).setPhotoMetaData(newPhoto);
        verify(storageService).storePhoto(argThat(photo -> photo.getFileName().equals("new.jpg")
                && photo.getFileContentType().equals("image/jpeg")));
    }

    @Test
    void wrapsStorageIoFailure() throws Exception {
        var productService = mock(ProductService.class);
        var productRepository = mock(ProductRepository.class);
        var storageService = mock(PhotoStorageService.class);
        var mapper = mock(ProductPhotoMapper.class);
        var useCase = new UploadProductPhotoUseCase(productService, productRepository, storageService, mapper);
        var input = new ProductPhotoInputDTO("Dish photo",
                new MockMultipartFile("file", "dish.jpg", "image/jpeg", new byte[]{1, 2}));
        input.setProductId(UUID.randomUUID());
        var product = mock(Product.class);
        var newPhoto = new ProductPhoto("new.jpg", "Dish photo", "image/jpeg", 2L);
        when(mapper.toProductPhoto(input)).thenReturn(newPhoto);
        when(productService.findValidProduct(input.getProductId())).thenReturn(product);
        when(product.getPhotoMetaData()).thenReturn(null, newPhoto);
        when(productRepository.saveAndFlush(product)).thenReturn(product);
        org.mockito.Mockito.doThrow(new IOException("storage failure")).when(storageService).storePhoto(any());

        assertThrows(FailOnHandleFileException.class, () -> useCase.execute(input));

        verify(storageService, never()).removePhoto(any());
    }
}
