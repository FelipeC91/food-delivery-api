package com.mypersonalportifolio.food_delivery_auth_server.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import com.mypersonalportifolio.food_delivery_auth_server.infraestructure.jpa.JpaUserDetailsService;

import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 1. Filter Chain para os endpoints do protocolo OAuth2 / OIDC
    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository,
            OAuth2AuthorizationService authorizationService
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .securityContext(context -> context.securityContextRepository(securityContextRepository))
                // 1. Ativa nativamente o Servidor de Autorização na DSL do Spring Security 7+
                .with(authorizationServerConfigurer, authorizationServer -> authorizationServer
                        .authorizationService(authorizationService)
                        .oidc(Customizer.withDefaults()) // Ativa o OpenID Connect 1.0 por padrão
                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                // 2. Redireciona para a página de login caso o usuário final não esteja autenticado
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                // 3. Permite validar tokens de acesso para os endpoints internos (ex: UserInfo)
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository,
            DaoAuthenticationProvider userAuthenticationProvider
    ) throws Exception {
        http
                .securityContext(context -> context.securityContextRepository(securityContextRepository))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(userAuthenticationProvider)
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

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

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails userDetails = User.withUsername("user")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    // 4. Repositório de Clientes cadastrados (Aplicações que vão consumir as APIs)
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("food-delivery-customer")
//                .clientSecret(this.passwordEncoder().encode("verysecret"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
    //                .tokenSettings(TokenSettings.builder()
    //                        .accessTokenTimeToLive(Duration.ofMinutes(30))
    //                        .refreshTokenTimeToLive(Duration.ofHours(12))
    //                        .build()
    //                )
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("http://127.0.0.1:8080/")
//       //         .postLogoutRedirectUri("http://127.0.0")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .scope("food-delivery-api")
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(true)
//                        .requireProofKey(true)
//                        .build())
//                .build();
//
//        RegisteredClient oidcClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("food-delivery-restaurant")
//                .clientSecret(this.passwordEncoder().encode("verysecret"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenTimeToLive(Duration.ofMinutes(30))
//                        .refreshTokenTimeToLive(Duration.ofHours(12))
//                        .build()
//                )
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("http://127.0.0.1:8080/")
//                //         .postLogoutRedirectUri("http://127.0.0")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .scope("food-delivery-api")
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(true)
//                        .requireProofKey(true)
//                        .build())
//                .build();
//
//        return new InMemoryRegisteredClientRepository(oidcClient, oidcClient2);
//    }

    // 7. Configurações gerais do servidor de autorização (ex: endpoint do Issuer)
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(
            JpaUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService() {
        // Códigos e refresh tokens ficam somente na memória deste processo.
        return new InMemoryOAuth2AuthorizationService();
    }
}
