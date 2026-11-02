package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenApiConfigTest {

    @Test
    void registersProblemDetailsReferencesAndAlternativeAuthenticationSchemes() {
        var operation = new Operation().responses(new ApiResponses());
        var openApi = new OpenAPI().paths(
                new Paths().addPathItem("/test", new PathItem().get(operation))
        );

        new OpenApiConfig().globalErrorResponseCustomizer().customise(openApi);

        var schemas = openApi.getComponents().getSchemas();
        assertNotNull(schemas.get("InvalidResourceField"));

        var problemDetails = schemas.get("ApiProblemDetails");
        var invalidResourceFields = (Schema<?>) problemDetails.getProperties().get("invalidResourceFields");
        assertEquals(
                "#/components/schemas/InvalidResourceField",
                invalidResourceFields.getItems().get$ref()
        );

        var securityRequirements = operation.getSecurity();
        assertEquals(2, securityRequirements.size());
        assertTrue(securityRequirements.get(0).containsKey("default_security_scheme"));
        assertTrue(securityRequirements.get(1).containsKey("Bearer_Auth"));
    }
}
