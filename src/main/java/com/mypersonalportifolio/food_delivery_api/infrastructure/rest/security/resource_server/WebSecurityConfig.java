package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // /Root entry point
                        .requestMatchers("/").authenticated()
                        // /food-categories
                        .requestMatchers(HttpMethod.POST,"/food-categories/**").hasAuthority("EDITAR_COZINHAS")
                        .requestMatchers(HttpMethod.PUT,"/food-categories/**").hasAuthority("EDITAR_COZINHAS")
                        .requestMatchers(HttpMethod.GET,"/food-categories/**").authenticated()
                        // /payment-methods
                        .requestMatchers(HttpMethod.GET,"/payment-methods/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")
                        .requestMatchers(HttpMethod.PUT,"/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")
                        .requestMatchers(HttpMethod.DELETE,"/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")
                        // states
                        .requestMatchers("/states/**").authenticated()
                        // cities
                        .requestMatchers("/cities/**").authenticated()
                        //
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
