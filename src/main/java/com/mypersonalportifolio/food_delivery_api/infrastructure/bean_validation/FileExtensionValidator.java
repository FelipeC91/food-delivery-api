package com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class FileExtensionValidator implements ConstraintValidator<FileExtension, MultipartFile> {

    private Set<String> allowedExtensions;

    @Override
    public void initialize(FileExtension constraintAnnotation) {
        this.allowedExtensions = Arrays.stream(constraintAnnotation.allowedExtensions())
                .map(String::trim)
                .map(this::parseNameToExtension)
                .map(extension -> extension.toLowerCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty() || value.getOriginalFilename() == null)
            return false;

        var extension = this.parseNameToExtension( value.getOriginalFilename() );

        return allowedExtensions.contains(extension);
    }

    private String parseNameToExtension(String extensionReference) {
        var separatorIndex = extensionReference.lastIndexOf("/");
        separatorIndex = separatorIndex == -1 ? extensionReference.lastIndexOf(".") : separatorIndex;

        return extensionReference.substring(separatorIndex + 1)
                                .toLowerCase(Locale.ROOT);

    }
}
