package com.algaworks.algafood.core.openapi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AlgaFoodApiErrorResponses {

    public static final String BAD_REQUEST = "BadRequest";
    public static final String NOT_ACCEPTABLE = "NotAcceptable";
    public static final String UNSUPPORTED_MEDIA_TYPE = "UnsupportedMediaType";
    public static final String INTERNAL_SERVER_ERROR = "InternalServerError";

}