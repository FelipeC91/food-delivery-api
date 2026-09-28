package com.mypersonalportifolio.food_delivery_api.infrastructure.bean_validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileExtensionValidatorTest {

    private FileExtensionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new FileExtensionValidator();
        validator.initialize(new FileExtension() {
            @Override
            public String[] allowedExtensions() {
                return new String[]{"jpg", ".png"};
            }

            @Override
            public String message() {
                return "";
            }

            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return FileExtension.class;
            }
        });
    }

    @Test
    void shouldAcceptConfiguredExtensionIgnoringCase() {
        var file = new MockMultipartFile("file", "photo.JPG", "image/jpeg", new byte[]{1});

        assertTrue(validator.isValid(file, null));
    }

    @Test
    void shouldRejectUnconfiguredExtension() {
        var file = new MockMultipartFile("file", "photo.gif", "image/gif", new byte[]{1});

        assertFalse(validator.isValid(file, null));
    }

    @Test
    void shouldRejectFilesWithoutExtension() {
        var file = new MockMultipartFile("file", "photo", "application/octet-stream", new byte[]{1});

        assertFalse(validator.isValid(file, null));
    }

    @Test
    void shouldTreatNullAsValidForUseWithNotNull() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void shouldRejectEmptyFiles() {
        var file = new MockMultipartFile("file", "photo.jpg", "image/jpeg", new byte[0]);

        assertFalse(validator.isValid(file, null));
    }
}
