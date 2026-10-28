package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.authorization_server;

import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "authorization-server.rsa-key-pair")
public record RsaKeyProperties(
        @Nullable
        String keyStorePassword,
        @NotNull
        String keyPairAlias,
        @NotNull
        String jksFilePath
) {}

