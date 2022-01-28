package com.algaworks.algafood.core.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<MediaType> allowed;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowed = MediaType.parseMediaTypes(List.of(constraintAnnotation.allowed()));
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        MediaType parseMediaType = MediaType.parseMediaType(value.getContentType());

        return allowed.stream().anyMatch(allowedMediaType -> allowedMediaType.isCompatibleWith(parseMediaType));
    }

}
