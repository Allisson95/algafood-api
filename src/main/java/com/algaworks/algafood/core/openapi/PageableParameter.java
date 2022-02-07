package com.algaworks.algafood.core.openapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
    in = ParameterIn.QUERY,
    name = "page",
    description = "Número da página (0..N)",
    schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
    in = ParameterIn.QUERY,
    name = "size",
    description = "Quantidade de elementos por página",
    schema = @Schema(type = "integer", defaultValue = "10")
)
@Parameter(
    in = ParameterIn.QUERY,
    name = "sort",
    description = "Critério de ordenação: propriedade(,asc|desc). A ordenação padrão é ascending. Multiplos critérios de ordenação são aceitos.",
    examples = {
        @ExampleObject("nome"),
        @ExampleObject("nome,asc"),
        @ExampleObject("nome,desc")
    },
    array = @ArraySchema(schema = @Schema(type = "string"))
)
public @interface PageableParameter { }
