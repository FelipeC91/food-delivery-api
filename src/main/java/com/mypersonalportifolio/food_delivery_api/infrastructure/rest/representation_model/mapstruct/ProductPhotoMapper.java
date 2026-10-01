package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.mapstruct;

import com.mypersonalportifolio.food_delivery_api.domain.model.ProductPhoto;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.input.ProductPhotoInputDTO;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.dto.output.ProductPhotoOutputDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Mapper(componentModel = "spring",  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductPhotoMapper {

    ProductPhotoMapper INSTANCE = Mappers.getMapper(ProductPhotoMapper.class);

    ProductPhotoOutputDTO toOutputDTO(ProductPhoto productPhoto);

    @Mapping(source = "file", target = "fileName",  qualifiedByName = "resolveFileName")
    @Mapping(source = "file.contentType", target = "contentType")
    @Mapping(source = "file.size", target = "fileSize")
    ProductPhoto toProductPhoto(ProductPhotoInputDTO productPhotoInputDTO);

    @Named("resolveFileName")
    default String resolveFileName(MultipartFile file) {

        return (UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
    }
}
