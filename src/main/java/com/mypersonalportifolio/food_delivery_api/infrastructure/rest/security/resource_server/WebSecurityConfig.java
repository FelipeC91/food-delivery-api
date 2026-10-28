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
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import static com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.WebConfig.API_BASE_PATH;

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
            .securityMatchers(matchers -> matchers
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/"))
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/food-categories/**"))
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/payment-methods/**"))
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/states/**"))
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/cities/**"))
            )
            .csrf(AbstractHttpConfigurer::disable)
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(apiAuthenticationEntryPoint)
                    .accessDeniedHandler(apiAccessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/").authenticated()

                    .requestMatchers(HttpMethod.POST, API_BASE_PATH + "/food-categories/**").hasAuthority("EDITAR_COZINHAS")
                    .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/food-categories/**").hasAuthority("EDITAR_COZINHAS")
                    .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/food-categories/**").authenticated()

                    .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/payment-methods/**").authenticated()
                    .requestMatchers(HttpMethod.POST, API_BASE_PATH + "/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")
                    .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")
                    .requestMatchers(HttpMethod.DELETE, API_BASE_PATH + "/payment-methods/**").hasAuthority("GERENCIAR_FORMAS_PAGAMENTO")

                    .requestMatchers(API_BASE_PATH + "/states/**").authenticated()
                    .requestMatchers(API_BASE_PATH + "/cities/**").authenticated()
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
            .securityMatchers(matchers -> matchers
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/restaurants"))
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/restaurants/**"))
            )
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(apiAuthenticationEntryPoint)
                    .accessDeniedHandler(apiAccessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/restaurants/{restaurantId}", "/restaurants").authenticated()

                    .requestMatchers(HttpMethod.PATCH, API_BASE_PATH + "/restaurants/{restaurantId}/opening", "/restaurants/{restaurantId}/activation")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/restaurants/{restaurantId}/managers")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "EDITAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/restaurants/{restaurantId}/managers")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.DELETE, API_BASE_PATH + "/restaurants/{restaurantId}/managers/{paymentMethodId}")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/restaurants/{restaurantId}/payment-methods").authenticated()

                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/restaurants/{restaurantId}/payment-methods/{paymentMethodId}")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.DELETE, API_BASE_PATH + "/restaurants/{restaurantId}/payment-methods/{paymentMethodId}")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/restaurants/{restaurantId}/products", "/restaurants/{restaurantId}/products/{productId}").authenticated()

                        .requestMatchers(HttpMethod.POST, API_BASE_PATH + "/restaurants/{restaurantId}/products")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "GERENCIAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/restaurants/{restaurantId}/products")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "EDITAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/restaurants/{restaurantId}/products/{productId}/photo").authenticated()

                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/restaurants/{restaurantId}/products/{productId}/photo")
                        .access((authenticationSupplier, context) -> customSecurityPolicies.checkRestaurantManager(authenticationSupplier, context, "EDITAR_RESTAURANTES"))

                        .requestMatchers(HttpMethod.DELETE, API_BASE_PATH + "/restaurants/{restaurantId}/products/{productId}/photo")
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
            // NOVIDADE: .securityMatchers (no plural) aceita múltiplos matchers via lambda
            .securityMatchers(matchers -> matchers
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/orders"))
                    .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/orders/**"))
            )
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(apiAuthenticationEntryPoint)
                    .accessDeniedHandler(apiAccessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/orders").access(customSecurityPolicies::checkAllOrdersAccess)
                    .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/orders/{orderId}").access(customSecurityPolicies::checkOrderAccess)
                    .requestMatchers(HttpMethod.POST, API_BASE_PATH + "/orders").authenticated()
                    .requestMatchers(HttpMethod.PATCH, API_BASE_PATH + "/orders/{orderId}/status")
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
                .securityMatchers(matchers -> matchers
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/users"))
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/users/**"))
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/groups"))
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/groups/**"))
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/permissions"))
                        .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher(API_BASE_PATH + "/permissions/**"))
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .accessDeniedHandler(apiAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/users").hasAuthority("GERENCIAR_ACESSOS")
                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/users/{userId}").access(customSecurityPolicies::checkIfHimself)
                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/users/{userId}").access(customSecurityPolicies::checkIfHimself)
                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/users/{userId}/password").access(customSecurityPolicies::checkIfCanAlterPassword)
                        .requestMatchers(HttpMethod.DELETE, API_BASE_PATH + "/users/{userId}").hasAuthority("GERENCIAR_USUARIOS")

                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/users/{userId}/groups").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/users/{userId}/groups/{groupId}").hasAuthority("GERENCIAR_USUARIOS")
                        .requestMatchers(HttpMethod.DELETE, API_BASE_PATH + "/users/{userId}/groups/{groupId}").hasAuthority("GERENCIAR_USUARIOS")

                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/groups").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/groups/{userGroupId}").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.POST, API_BASE_PATH + "/groups").hasAuthority("CONSULTAR_USUARIOS")
                        .requestMatchers(HttpMethod.PUT, API_BASE_PATH + "/groups/{userGroupId}").hasAuthority("GERENCIAR_USUARIOS")
                        .requestMatchers(HttpMethod.DELETE, API_BASE_PATH + "/groups/{userGroupId}").hasAuthority("GERENCIAR_USUARIOS")

                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/permissions").hasAuthority("CONSULTAR_USUARIOS")
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
                .securityMatcher( API_BASE_PATH + "/analytics/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(apiAuthenticationEntryPoint)
                        .accessDeniedHandler(apiAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/analytics/").hasAuthority("CONSULTAR_RELATORIOS")
                        .requestMatchers(HttpMethod.GET, API_BASE_PATH + "/analytics/{restaurantId}/daily-sales").access(customSecurityPolicies::checkIfCanAccessReports)
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
