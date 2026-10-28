package com.mypersonalportifolio.food_delivery_auth_server.infraestructure.config;

import com.mypersonalportifolio.food_delivery_auth_server.infraestructure.jpa.CustomUserDetails;
import com.mypersonalportifolio.food_delivery_auth_server.infraestructure.jpa.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class TokenCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(JpaUserDetailsService userDetailsService) {
        return context -> {
            if ("access_token".equals(context.getTokenType().getValue())) {
                Authentication authentication = context.getPrincipal();

                if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType())) {
                    var registeredClient = context.getRegisteredClient();
                    Authentication authorizationGrant = context.getAuthorizationGrant();
                    if (!(authorizationGrant instanceof OAuth2ClientCredentialsAuthenticationToken clientCredentialsGrant)) {
                        throw new IllegalStateException("Expected client_credentials grant authentication");
                    }

                    Object usernameParameter = clientCredentialsGrant.getAdditionalParameters().get("username");
                    if (!(usernameParameter instanceof String username) || username.isBlank()) {
                        throw new IllegalStateException("Missing username parameter in client_credentials request");
                    }

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (!(userDetails instanceof CustomUserDetails customUserDetails)) {
                        throw new IllegalStateException("Expected CustomUserDetails for user " + username);
                    }
                    Set<String> clientAuthorities = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet());

                    context.getClaims()
                            .claim("grant_type", AuthorizationGrantType.CLIENT_CREDENTIALS.getValue())
                            .claim("client_id", registeredClient.getClientId())
                            .claim("user_id", customUserDetails.getId())
                            .claim("user_name", customUserDetails.getFullUsername())
                            .claim("authorities", clientAuthorities);
                } else if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
                    var authorities = userDetails instanceof CustomUserDetails customUserDetails
                            ? customUserDetails.getAuthorities()
                            : authentication.getAuthorities();

                    Set<String> userAuthorities = authorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet());

                    context.getClaims()
                            .claim("grant_type", "authorization_code")
                            .claim("user_name", userDetails.getUsername())
                            .claim("authorities", userAuthorities);

                    if (userDetails instanceof CustomUserDetails customUserDetails) {
                        context.getClaims()
                                .claim("user_id", customUserDetails.getId())
                                .claim("user_name", customUserDetails.getFullUsername());
                    }
                }
            }
        };
    }
}
