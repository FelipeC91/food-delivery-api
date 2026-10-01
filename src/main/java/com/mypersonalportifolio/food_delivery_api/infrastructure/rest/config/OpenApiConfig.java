package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Food Delivery API",
                description = """
                        This API serves customers and restaurants. It covers the following actions:
                        
                        customers: registering, searching for restaurants, and making orders \n
                        restaurants: registering, managing their product's catalog, receiving orders, and processing them.
                        """,
                version = "1.0.0",
                contact = @Contact(name = "Felipe Campos", url = "github.com/FelipeC91")
        )
)
public class OpenApiConfig {
}
