package com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileMaxSize, MultipartFile> {

    private DataSize maxSize;

    @Override
    public void initialize(FileMaxSize constraintAnnotation) {
        this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return !value.isEmpty() || value.getSize() <= this.maxSize.toBytes();
    }
}
