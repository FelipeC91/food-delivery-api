package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final CustomSecurityPolicies customSecurityPolicies;
    private final ApiAccessDeniedHandler apiAccessDeniedHandler;
    private final ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;

    public WebSecurityConfig(CustomSecurityPolicies customSecurityPolicies,
                             ApiAccessDeniedHandler apiAccessDeniedHandler,
                             ApiAuthenticationEntryPoint apiAuthenticationEntryPoint) {
        this.customSecurityPolicies = customSecurityPolicies;
        this.apiAccessDeniedHandler = apiAccessDeniedHandler;
        this.apiAuthenticationEntryPoint = apiAuthenticationEntryPoint;
    }

@Bean
@Order(2)
public SecurityFilterChain generalResourcesFilterChain(HttpSecurity http) throws Exception {

    http
            .securityMatcher("/", "/food-categories/**", "/payment-methods/**", "/states/**", "/cities/**")
            .csrf(AbstractHttpConfigurer::disable)
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(apiAuthenticationEntryPoint)
                    .accessDeniedHandler(apiAccessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/").authenticated()

                    .requestMatchers(HttpMethod.POST, "/food-categories/**").hasAuthority("EDITAR_COZINHAS")
                    .requestMatchers(HttpMethod.PUT, "/food-categories/**").hasAuthority("EDITAR_COZINHAS")
                    .requestMatchers(HttpMethod.GET, "/food-categories/**").authenticated()

                    .requestMatchers(HttpMethod.GET, "/payment-methods/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")
                    .requestMatchers(HttpMethod.PUT, "/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")
                    .requestMatchers(HttpMethod.DELETE, "/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")

                    .requestMatchers("/states/**").authenticated()
                    .requestMatchers("/cities/**").authenticated()
                    .anyRequest().denyAll()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                    .authenticationEntryPoint(apiAuthenticationEntryPoint)
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
}

    @Bean
@Order(3)
public SecurityFilterChain restaurantsFilterChain(HttpSecurity http) throws Exception {
    http
            .securityMatcher("/restaurants", "/restaurants/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .accessDeniedHandler(apiAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/restaurants/{restaurantId}", "/restaurants").authenticated()

                        .requestMatchers(HttpMethod.PATCH, "/restaurants/{restaurantId}/opening", "/restaurants/{restaurantId}/activation")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, "/restaurants/{restaurantId}/managers")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "EDITAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.PUT, "/restaurants/{restaurantId}/managers")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.DELETE, "/restaurants/{restaurantId}/managers/{paymentMethodId}")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, "/restaurants/{restaurantId}/payment-methods").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/restaurants/{restaurantId}/payment-methods/{paymentMethodId}")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.DELETE, "/restaurants/{restaurantId}/payment-methods/{paymentMethodId}")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, "/restaurants/{restaurantId}/products", "/restaurants/{restaurantId}/products/{productId}").authenticated()

                        .requestMatchers(HttpMethod.POST, "/restaurants/{restaurantId}/products")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.PUT, "/restaurants/{restaurantId}/products")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "EDITAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, "/restaurants/{restaurantId}/products/{productId}/photo").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/restaurants/{restaurantId}/products/{productId}/photo")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "EDITAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.DELETE, "/restaurants/{restaurantId}/products/{productId}/photo")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "EDITAR_RESTAURANTES"))

                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
@Order(4)
public SecurityFilterChain ordersFilterChain(HttpSecurity http) throws Exception {
    http
            .securityMatcher("/api/v*/orders", "/api/v*/orders/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .accessDeniedHandler(apiAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v*/orders").access(customSecurityPolicies::checkAllOrdersAccess)
                        .requestMatchers(HttpMethod.GET, "/api/v*/orders/{orderId}").access(customSecurityPolicies::checkOrderAccess)
                        .requestMatchers(HttpMethod.POST, "/api/v*/orders").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/v*/orders/{orderId}/status")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkIfOrderBelongsToRestaurantPolicy(authenticationSupplier, context, "GERENCIAR_PEDIDOS"))
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }
    @Bean
@Order(5)
public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
    http
                .securityMatcher("/users", "/users/**", "/user", "/user/**", "/groups", "/groups/**", "/permissions")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .accessDeniedHandler(apiAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/users", "/user").hasAuthority("GERENCIAR_ACESSOS")
                        .requestMatchers(HttpMethod.GET, "/users/{userId}", "/user/{userId}").access(customSecurityPolicies::checkIfHimself)
                        .requestMatchers(HttpMethod.PUT, "/users/{userId}", "/user/{userId}").access(customSecurityPolicies::checkIfHimself)
                        .requestMatchers(HttpMethod.PUT, "/users/{userId}/password", "/user/{userId}/password").access(customSecurityPolicies::checkIfCanAlterPassword)
                        .requestMatchers(HttpMethod.DELETE, "/users/{userId}", "/user/{userId}").hasAuthority("GERENCIAR_USUARIOS")

                        .requestMatchers(HttpMethod.GET, "/users/{userId}/groups").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.PUT, "/users/{userId}/groups/{groupId}").hasAuthority("GERENCIAR_USUARIOS")
                        .requestMatchers(HttpMethod.DELETE, "/users/{userId}/groups/{groupId}").hasAuthority("GERENCIAR_USUARIOS")

                        .requestMatchers(HttpMethod.GET, "/groups").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.GET, "/groups/{userGroupId}").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.POST, "/groups").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.PUT, "/groups/{userGroupId}").hasAuthority("GERENCIAR_USUARIOS")
                        .requestMatchers(HttpMethod.DELETE, "/groups/{userGroupId}").hasAuthority("GERENCIAR_USUARIOS")

                        .requestMatchers(HttpMethod.GET, "/permissions").hasAuthority("CONSULTAR_USUARIOS")
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
@Order(6)
public SecurityFilterChain analyticsFilterChain(HttpSecurity http) throws Exception {
    http
                .securityMatcher( "/analytics/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .accessDeniedHandler(apiAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/analytics/").hasAuthority("CONSULTAR_RELATORIOS")
                        .requestMatchers(HttpMethod.GET, "/analytics/{restaurantId}/daily-sales").access(customSecurityPolicies::checkIfCanAccessReports)
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthoritiesClaimName(JwtClaimKeys.AUTHORITIES.getKey());
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
