package com.mypersonalportifolio.food_delivery_api.infrastructure.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Food Delivery API",
                description = """
                        This API serves customers and restaurants. It covers the following actions:
                        
                        customers: registering, searching for restaurants, and making orders \n
                        restaurants: registering, managing their products' catalog, receiving orders, and processing them.
                        """,
                version = "1.0.0",
                contact = @Contact(name = "Felipe Campos", url = "https://github.com/FelipeC91")
        )
)
public class OpenApiConfig {

        @Bean
        public OpenApiCustomizer globalErrorResponseCustomizer() {
                return openApi -> openApi.getPaths().values().forEach(pathItem ->
                        pathItem.readOperations().forEach(operation -> {
                                var apiResponses = operation.getResponses();

                                // 1. Create a global 400 Bad Request response
                                var badRequest = new ApiResponse()
                                        .description("Bad Request - The client provided invalid data.")
                                        .content(new Content().addMediaType("application/json",
                                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))));

                                // 2. Create a global 500 Internal Server Error response
                                var internalServerError = new ApiResponse()
                                        .description("Internal Server Error - Something went wrong on our side.")
                                        .content(new Content().addMediaType("application/json",
                                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))));

                                // 3. Inject them into the operation
                                apiResponses.addApiResponse("400", badRequest);
                                apiResponses.addApiResponse("500", internalServerError);
                        })
                );
        }

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Bearer_Auth"; // Nome exato do vínculo

        return new OpenAPI()
                // 1. Tranca todos os endpoints globalmente (Cadeados fechados)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // 2. Define o campo para inserção do token
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT") // Opcional: dica visual para o desenvolvedor
                                .description("Insira o seu token JWT gerado (ex: obtido via Postman ou endpoint de login)")));
    }


}
