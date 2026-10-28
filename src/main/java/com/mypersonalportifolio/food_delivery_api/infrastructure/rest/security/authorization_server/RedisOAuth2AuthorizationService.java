/*
 * Redis-backed authorization storage is disabled. The active implementation
 * is InMemoryOAuth2AuthorizationService, configured in SecurityConfig.
 *
 * @Service
 * public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {
 *     // Redis save/find/remove implementation intentionally inactive.
 * }
 */