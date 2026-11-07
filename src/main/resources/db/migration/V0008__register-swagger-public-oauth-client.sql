INSERT INTO oauth2_registered_client (
    id,
    client_id,
    client_id_issued_at,
    client_name,
    client_authentication_methods,
    authorization_grant_types,
    redirect_uris,
    post_logout_redirect_uris,
    scopes,
    client_settings,
    token_settings
) VALUES (
    'food-delivery-swagger-ui',
    'food-delivery-swagger-ui',
    CURRENT_TIMESTAMP,
    'Food Delivery Swagger UI',
    'none',
    'authorization_code',
    'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://localhost:8080/swagger-ui/oauth2-redirect.html',
    NULL,
    'openid,profile',
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true,"settings.client.require-authorization-consent":true}',
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.refresh-token-time-to-live":["java.time.Duration",43200.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.x509-certificate-bound-access-tokens":false}'
);
