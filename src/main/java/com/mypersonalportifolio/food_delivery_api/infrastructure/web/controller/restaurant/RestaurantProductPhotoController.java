package com.mypersonalportifolio.food_delivery_api.infrastructure.web.controller.restaurant;

import com.mypersonalportifolio.food_delivery_api.application.use_case.RemoveProductPhotoUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.RetrieveProductPhotoUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.UploadProductPhotoUseCase;
import com.mypersonalportifolio.food_delivery_api.domain.model.ProductPhoto;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.input.ProductPhotoInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.web.representation_model.dto.output.ProductPhotoOutputDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequestMapping("restaurants/{restaurantId}/products/{productId}/photo")
@RestController
public class RestaurantProductPhotoController {

    private final UploadProductPhotoUseCase uploadProductPhotoUseCase;
    private final RetrieveProductPhotoUseCase retrieveProductPhotoUseCase;
    private final RemoveProductPhotoUseCase removeProductPhotoUseCase;

    @Autowired
    public RestaurantProductPhotoController(UploadProductPhotoUseCase uploadProductPhotoUseCase, RetrieveProductPhotoUseCase retrieveProductPhotoUseCase, RemoveProductPhotoUseCase removeProductPhotoUseCase) {
        this.uploadProductPhotoUseCase = uploadProductPhotoUseCase;
        this.retrieveProductPhotoUseCase = retrieveProductPhotoUseCase;
        this.removeProductPhotoUseCase = removeProductPhotoUseCase;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductPhoto acceptPhoto(@PathVariable UUID restaurantId,
                                    @PathVariable UUID productId,
                                    @Valid @ModelAttribute ProductPhotoInputDTO productPhotoInputDTO) {
        productPhotoInputDTO.setProductId(productId);
        productPhotoInputDTO.setRestaurantId(restaurantId);

        return uploadProductPhotoUseCase.execute(productPhotoInputDTO);
    }


    @GetMapping
    public ResponseEntity<?> getResource(@PathVariable UUID restaurantId,
                                                           @PathVariable UUID productId,
                                                           @RequestHeader("Accept") String requestAccepts) throws HttpMediaTypeNotAcceptableException {
        ProductPhotoOutputDTO storedPhoto;
        try {
            storedPhoto = retrieveProductPhotoUseCase.execute(productId);

        } catch (IllegalStateException e) { //if it has no photo
            return ResponseEntity.noContent().build();
        }

        var s3UrlOptional =storedPhoto.getFileDetails().getFileS3Location();

        if (s3UrlOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.CONTENT_LOCATION, s3UrlOptional.get().toString())
                    .build();

        } else {

            var productPhotoMediaType = MediaType.parseMediaType(storedPhoto.getContentType());

            checkIfFileMediaTypeIsAcceptable(requestAccepts, productPhotoMediaType);

            var fileInputStream = storedPhoto.getFileDetails().getFileInputStream().get();

            return ResponseEntity.ok()
                    .contentType(productPhotoMediaType)
                    .contentLength(storedPhoto.getFileSize())
                    .body(new InputStreamResource( fileInputStream ));
        }
    }

    private void checkIfFileMediaTypeIsAcceptable(String requestAccepts, MediaType sourceFileMediaType) throws HttpMediaTypeNotAcceptableException {
        var requiredMediaTypes = Arrays.stream(Objects.requireNonNull(requestAccepts).split(","))
                .map(MediaType::valueOf)
                .toList();

        if (isNotAcceptableResponseMediaType(requiredMediaTypes, sourceFileMediaType))
            throw new HttpMediaTypeNotAcceptableException(requiredMediaTypes);
    }

    private boolean isNotAcceptableResponseMediaType(List<MediaType> mediaTypes, MediaType availableMediaType) {
        return ( mediaTypes.stream().noneMatch(availableMediaType::isCompatibleWith) && !mediaTypes.contains(MediaType.ALL) );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteProductPhoto(@PathVariable UUID restaurantId, @PathVariable UUID productId) {
        removeProductPhotoUseCase.execute(productId);
    }

}
