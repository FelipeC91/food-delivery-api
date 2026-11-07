UPDATE oauth2_registered_client
SET client_settings = REPLACE(
    client_settings,
    '"settings.client.require-authorization-consent":true',
    '"settings.client.require-authorization-consent":false'
)
WHERE client_id IN (
    'food-delivery-swagger-ui',
    'food-delivery-customer',
    'food-delivery-restaurant'
);
