package com.mypersonalportifolio.food_delivery_api.application.use_case.dto;

import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.FileExtension;
import com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation.FileMaxSize;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "Description of the product photo", example = "Lamen")
        @NotBlank
        private final String description;


        @Schema(type = "string", format = "binary", description = "JPEG or PNG photo file")
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
