package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.dto.ProductPhotoInputDTO;
import com.mypersonalportifolio.food_delivery_api.domain.model.ProductPhoto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.util.UUID;

@Tag(name = "Products")
public interface RestaurantProductPhotoControllerOpenApi {
    @Operation(summary = "Upload a product photo",
            description = "Uploads a JPEG or PNG image and description for a restaurant product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product photo uploaded"),
            @ApiResponse(responseCode = "400", description = "The photo or metadata is invalid"),
            @ApiResponse(responseCode = "404", description = "Restaurant or product not found")
    })
    ProductPhoto acceptPhoto(
            @Parameter(name = "restaurantId", in = ParameterIn.PATH, description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(name = "productId", in = ParameterIn.PATH, description = "Product identifier", required = true) UUID productId,
            @RequestBody(
                    description = "Photo file and its description",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = ProductPhotoInputDTO.class)
                    )
            ) ProductPhotoInputDTO productPhotoInputDTO);

    @Operation(summary = "Retrieve a product photo",
            description = "Returns the product image, redirects to its storage location when applicable, or returns no content if no photo exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Image returned",
                    content = @Content(mediaType = "image/*", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "302", description = "Image available at the Content-Location"),
            @ApiResponse(responseCode = "204", description = "No photo is associated with this product"),
            @ApiResponse(responseCode = "406", description = "The requested image media type is not available"),
            @ApiResponse(responseCode = "404", description = "Restaurant or product not found")
    })
    ResponseEntity<?> getResource(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(description = "Product identifier", required = true) UUID productId,
            @Parameter(name = "Accept", in = ParameterIn.HEADER,
                    description = "Accepted image media types", required = true, example = "image/jpeg")
            String requestAccepts) throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Delete a product photo", description = "Removes the photo associated with the specified product.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product photo deleted"),
            @ApiResponse(responseCode = "404", description = "Restaurant or product not found")
    })
    void deleteProductPhoto(
            @Parameter(description = "Restaurant identifier", required = true) UUID restaurantId,
            @Parameter(description = "Product identifier", required = true) UUID productId);
}
