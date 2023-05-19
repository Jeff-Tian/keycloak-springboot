package com.example.keycloakspringboot.config;

import io.swagger.annotations.*;

@SwaggerDefinition(
        info = @Info(
                description = "Gets the weather",
                version = "V12.0.12",
                title = "The Weather API",
                termsOfService = "http://theweatherapi.io/terms.html",
                contact = @Contact(
                        name = "Rain Moore",
                        email = "rain.moore@theweatherapi.io",
                        url = "http://theweatherapi.io"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Private", description = "Tag used to denote operations as private")
        },
        externalDocs = @ExternalDocs(value = "Meteorology", url = "http://theweatherapi.io/meteorology.html")
)
public interface SwaggerConfig {
}
