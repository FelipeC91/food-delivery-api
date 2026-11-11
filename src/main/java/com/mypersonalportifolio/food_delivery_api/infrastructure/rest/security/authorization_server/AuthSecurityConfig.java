package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.authorization_server;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.authorization_server.jpa.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class AuthSecurityConfig {
    private static final String CANONICAL_AUTHORIZATION_SERVER_URL = "http://127.0.0.1:8080";

    @Bean
    @Order(0)
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/v3/api-docs", "/v3/api-docs/**", "/swagger-ui/oauth2-redirect.html","/actuator/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

        return http.build();
    }

    // 1. Filter Chain para os endpoints do protocolo OAuth2 / OIDC
    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository,
            OAuth2AuthorizationService authorizationService,
            OAuth2AuthorizationConsentService authorizationConsentService,
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .securityContext(context -> context.securityContextRepository(securityContextRepository))
                // 1. Ativa nativamente o Servidor de Autorização na DSL do Spring Security 7+
                .with(authorizationServerConfigurer, authorizationServer -> authorizationServer
                        .authorizationService(authorizationService)
                        .authorizationConsentService(authorizationConsentService)
                        .oidc(Customizer.withDefaults()) // Ativa o OpenID Connect 1.0 por padrão
                )
                .authorizeHttpRequests(authorize -> authorize
                        // The token endpoint performs OAuth client authentication itself.
                        .requestMatchers("/oauth2/token").permitAll()
                        .anyRequest().authenticated())
                // 2. Redireciona para a página de login caso o usuário final não esteja autenticado
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint(CANONICAL_AUTHORIZATION_SERVER_URL + "/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                // 3. Permite validar tokens de acesso para os endpoints internos (ex: UserInfo)
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        http.addFilterBefore(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                if ("localhost".equals(request.getServerName())
                        && "/oauth2/authorize".equals(request.getRequestURI())) {
                    var query = request.getQueryString();
                    var location = CANONICAL_AUTHORIZATION_SERVER_URL + request.getRequestURI()
                            + (query == null ? "" : "?" + query);
                    response.sendRedirect(location);
                    return;
                }

                filterChain.doFilter(request, response);
            }
        }, SecurityContextHolderFilter.class);

        return http.build();
    }

    @Bean
    @Order(7)
    public SecurityFilterChain loginSecurityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository,
            DaoAuthenticationProvider userAuthenticationProvider
    ) throws Exception {
        http
                .securityContext(context -> context.securityContextRepository(securityContextRepository))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(userAuthenticationProvider);

        return http.build();
    }


    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    // 3. Definição do usuário em memória (Dono do Recurso)
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    // 7. Configurações gerais do servidor de autorização (ex: endpoint do Issuer)
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(JpaUserDetailsService userDetailsService,
                                                            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations
            , RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(JdbcOperations jdbcOperations,
            RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
    }
}
