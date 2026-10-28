/*
 * Redis configuration is disabled. OAuth2 authorizations are stored in the
 * process-local InMemoryOAuth2AuthorizationService configured in SecurityConfig.
 *
 * @Configuration
 * public class RedisConfig {
 *     @Bean
 *     public RedisTemplate<String, Object> redisTemplate(
 *             RedisConnectionFactory connectionFactory) {
 *         RedisTemplate<String, Object> template = new RedisTemplate<>();
 *         template.setConnectionFactory(connectionFactory);
 *         template.setKeySerializer(new StringRedisSerializer());
 *         template.setValueSerializer(new JdkSerializationRedisSerializer());
 *         template.setHashKeySerializer(new StringRedisSerializer());
 *         template.setHashValueSerializer(new JdkSerializationRedisSerializer());
 *         return template;
 *     }
 * }
 */