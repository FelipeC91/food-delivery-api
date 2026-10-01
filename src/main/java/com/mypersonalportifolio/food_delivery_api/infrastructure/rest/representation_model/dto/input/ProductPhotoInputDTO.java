package com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input;

import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.FileExtension;
import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.FileMaxSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductPhotoInputDTO {
        @NotBlank
        private final String description;

        @FileMaxSize
        @FileExtension(allowedExtensions = {
                "image/jpg",
                MediaType.IMAGE_PNG_VALUE,
                MediaType.IMAGE_JPEG_VALUE}
        )
        @NotNull
        private final MultipartFile file;

        @Setter
        UUID productId;

        @Setter
        UUID restaurantId;
}
