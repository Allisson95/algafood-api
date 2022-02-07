package com.algaworks.algafood.core.openapi;

import static com.algaworks.algafood.core.openapi.AlgaFoodApiErrorResponses.BAD_REQUEST;
import static com.algaworks.algafood.core.openapi.AlgaFoodApiErrorResponses.INTERNAL_SERVER_ERROR;
import static com.algaworks.algafood.core.openapi.AlgaFoodApiErrorResponses.NOT_ACCEPTABLE;
import static com.algaworks.algafood.core.openapi.AlgaFoodApiErrorResponses.UNSUPPORTED_MEDIA_TYPE;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.CIDADES;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.COZINHAS;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.ESTADOS;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.ESTATISTICAS;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.FORMAS_PAGAMENTO;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.GRUPOS;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.PEDIDOS;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.PRODUTOS;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.RESTAURANTES;
import static com.algaworks.algafood.core.openapi.AlgaFoodTags.USUARIOS;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .tags(tags())
                .components(new Components()
                        .schemas(schemas())
                        .responses(responses()));
    }

    @Bean
    public OpenApiCustomiser globalResponses() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperationsMap().entrySet().forEach(operationEntry -> {
                        HttpMethod method = operationEntry.getKey();
                        ApiResponses responses = operationEntry.getValue().getResponses();

                        if (method.compareTo(HttpMethod.GET) == 0) {
                            defaultGetResponses().accept(responses);
                        } else if (method.compareTo(HttpMethod.POST) == 0) {
                            defaultPostPutResponses().accept(responses);
                        } else if (method.compareTo(HttpMethod.PUT) == 0) {
                            defaultPostPutResponses().accept(responses);
                        } else if (method.compareTo(HttpMethod.DELETE) == 0) {
                            defaultDeleteResponses().accept(responses);
                        }
                })
            );

            openApi.setTags(tags()); // Atribui as tags novamente para limpar as duplicadas que a lib está gerando
        };
    }

    private Info apiInfo() {
        return new Info().title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("v0.0.1")
                .contact(new Contact()
                        .name("AlgaFood")
                        .url("https://www.algafood.com.br")
                        .email("contato@algafood.com.br"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org"));
    }

    private List<Tag> tags() {
        return Arrays.asList(
                createTag(CIDADES, "Gerencia as cidades"),
                createTag(GRUPOS, "Gerencia os grupos de usuários"),
                createTag(COZINHAS, "Gerencia as cozinhas"),
                createTag(FORMAS_PAGAMENTO, "Gerencia as formas de pagamento"),
                createTag(PEDIDOS, "Gerencia os pedidos"),
                createTag(RESTAURANTES, "Gerencia os restaurantes"),
                createTag(ESTADOS, "Gerencia os estados"),
                createTag(PRODUTOS, "Gerencia os produtos de restaurantes"),
                createTag(USUARIOS, "Gerencia os usuários"),
                createTag(ESTATISTICAS, "Estatísticas da AlgaFood"));
    }

    private Consumer<ApiResponses> defaultGetResponses() {
        return responses -> {
            responses.addApiResponse("406", new ApiResponse().$ref(NOT_ACCEPTABLE));
            responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR));
        };
    }

    private Consumer<ApiResponses> defaultPostPutResponses() {
        return responses -> {
            responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST));
            responses.addApiResponse("406", new ApiResponse().$ref(NOT_ACCEPTABLE));
            responses.addApiResponse("415", new ApiResponse().$ref(UNSUPPORTED_MEDIA_TYPE));
            responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR));
        };
    }

    private Consumer<ApiResponses> defaultDeleteResponses() {
        return responses -> {
            responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST));
            responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_SERVER_ERROR));
        };
    }

    private Map<String, Schema> schemas() {
        final Map<String, Schema> schemas = new LinkedHashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemas.putAll(problemSchema);
        schemas.putAll(problemObjectSchema);

        return schemas;
    }

    private Map<String, ApiResponse> responses() {
        final Map<String, ApiResponse> responses = new LinkedHashMap<>();

        responses.put(BAD_REQUEST, createErrorResponse("Requisição inválida (erro do cliente)"));
        responses.put(NOT_ACCEPTABLE, new ApiResponse().description("Recurso não possui representação que pode ser aceita pelo consumidor"));
        responses.put(UNSUPPORTED_MEDIA_TYPE, createErrorResponse("Requisição recusada porque o corpo está em um formato não suportado"));
        responses.put(INTERNAL_SERVER_ERROR, createErrorResponse("Erro interno do Servidor"));

        return responses;
    }

    private Tag createTag(String name, String description) {
        return new Tag()
            .name(name)
            .description(description);
    }

    private ApiResponse createErrorResponse(String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content()
                        .addMediaType("application/problem+json", new MediaType()
                                .schema(new Schema<Problem>().$ref("Problema"))));
    }

}
