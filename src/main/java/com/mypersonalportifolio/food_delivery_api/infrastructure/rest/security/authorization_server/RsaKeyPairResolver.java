package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.authorization_server;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.encrypt.KeyStoreKeyFactory;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableConfigurationProperties(RsaKeyProperties.class)
public class RsaKeyPairResolver {
    // JWK keys provider to sign JWT - it only works when there will a single instance of authorization server
    private final RsaKeyProperties  rsaKeyProperties;
    private final ResourceLoader resourceLoader;

    public RsaKeyPairResolver(RsaKeyProperties rsaKeyProperties, ResourceLoader resourceLoader) {
        this.rsaKeyProperties = rsaKeyProperties;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
//        KeyPair keyPair = generateRsaKey();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);

        var jksResource = resourceLoader.getResource(rsaKeyProperties.jksFilePath());
        var keyStoreFactory =  new KeyStoreKeyFactory(jksResource, rsaKeyProperties.keyStorePassword().toCharArray());
        var rsaKeyPair =keyStoreFactory.getKeyPair(rsaKeyProperties.keyPairAlias());

        var rsaKey = new RSAKey.Builder((RSAPublicKey) rsaKeyPair.getPublic())
                .privateKey((RSAPrivateKey) rsaKeyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();

        return (jwkSelector, securityContext) -> jwkSelector.select(new JWKSet(rsaKey));
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    // JWT decoder to validate signatures
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
