package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.exception_handler.ApiProblemDetails;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
@SecuritySchemes({
        @SecurityScheme(name = "default_security_scheme",
                type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(authorizationCode =
                            @OAuthFlow(authorizationUrl ="${springdoc.swagger-ui.oauth.authorization-url}",
                                    tokenUrl = "${springdoc.swagger-ui.oauth.token-url}",
                                    scopes = @OAuthScope( name = "openid")
                            )
                )
        ),
        @SecurityScheme(name = "Bearer_Auth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                description = "Authenticate using a JWT bearer token"
        )
})
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer pinDocsToVersionOne() {
        return openApi -> {
            var versionedPaths = openApi.getPaths().entrySet().stream()
                    .filter(entry -> entry.getKey().contains("/v{version}"))
                    .toList();

            versionedPaths.forEach(entry -> {
                openApi.getPaths().remove(entry.getKey());
                openApi.getPaths().addPathItem(
                        entry.getKey().replace("/v{version}", "/v1"),
                        entry.getValue()
                );
            });
        };
    }

        @Bean
        public OpenApiCustomizer globalErrorResponseCustomizer() {
                return openApi -> {
                        var components = openApi.getComponents();
                        if (components == null) {
                                components = new Components();
                                openApi.setComponents(components);
                        }
                        ModelConverters.getInstance().read(ApiProblemDetails.class)
                                .forEach(components::addSchemas);
                        var invalidResourceFieldSchema = ModelConverters.getInstance()
                                .read(ApiProblemDetails.InvalidResourceField.class)
                                .values()
                                .stream()
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException(
                                        "Could not resolve the InvalidResourceField OpenAPI schema"
                                ));
                        components.addSchemas("InvalidResourceField", invalidResourceFieldSchema);

                        var problemDetailsSchema = new Schema<>().$ref("#/components/schemas/ApiProblemDetails");
                        var authenticationMethods = List.of(
                                new SecurityRequirement().addList("default_security_scheme", "openid"),
                                new SecurityRequirement().addList("Bearer_Auth")
                        );

                        openApi.getPaths().values().forEach(pathItem ->
                        pathItem.readOperations().forEach(operation -> {
                                var apiResponses = operation.getResponses();
                                operation.setSecurity(authenticationMethods);

                                // 1. Create a global 400 Bad Request response
                                var badRequest = new ApiResponse()
                                        .description("Bad Request - The client provided invalid data.")
                                        .content(new Content().addMediaType("application/json",
                                                new MediaType().schema(problemDetailsSchema)));

                                // 2. Create a global 500 Internal Server Error response
                                var internalServerError = new ApiResponse()
                                        .description("Internal Server Error - Something went wrong on our side.")
                                        .content(new Content().addMediaType("application/json",
                                                new MediaType().schema(problemDetailsSchema)));

                                // 3. Inject them into the operation
                                apiResponses.addApiResponse("400", badRequest);
                                apiResponses.addApiResponse("500", internalServerError);
                        })
                        );
                };
        }

//    @Bean
//    public OpenAPI customOpenAPI() {
//        final String securitySchemeName = "Bearer_Auth"; // Nome exato do vínculo
//
//        return new OpenAPI()
//                // 1. Tranca todos os endpoints globalmente (Cadeados fechados)
//                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//                // 2. Define o campo para inserção do token
//                .components(new Components()
//                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT") // Opcional: dica visual para o desenvolvedor
//                                .description("Insira o seu token JWT gerado (ex: obtido via Postman ou endpoint de login)")));
//    }


}
