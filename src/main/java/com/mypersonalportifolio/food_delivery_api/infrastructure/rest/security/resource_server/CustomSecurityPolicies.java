package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server;

import com.mypersonalportifolio.food_delivery_api.domain.repository.OrderRepository;
import com.mypersonalportifolio.food_delivery_api.domain.repository.RestaurantRepository;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Component("myCustomSecurityPolicies")
public class CustomSecurityPolicies {

    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    public CustomSecurityPolicies(RestaurantRepository restaurantRepository, OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    public AuthorizationDecision checkRestaurantManager(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext context, String requiredPermission) {
        var authentication = authenticationSupplier.get();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return new AuthorizationDecision(false);
        }

        assert context != null;
        var pathVariables = context.getVariables();

        var restaurantTargetId = Objects.nonNull(pathVariables.get("restaurantId")) ? pathVariables.get("restaurantId") : context.getRequest().getParameter("restaurantId");
        var userId =  extractRequestUser(jwtAuth);

        var isManager = restaurantRepository.existsManagerByRestaurantIdAndManagerId(UUID.fromString(restaurantTargetId), userId);

        var  hasAuthority = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(requiredPermission));

        return new AuthorizationDecision(isManager && hasAuthority);
    }

    public AuthorizationDecision checkAllOrdersAccess(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        var authentication = authenticationSupplier.get();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth))
            return new AuthorizationDecision(false);


        if ( this.checkIfCanAccessAnyOrderPolicy(authentication) )
            return new AuthorizationDecision(true);

        var userIdOptional   = Optional.ofNullable(context.getRequest().getParameter("customerId"));
        var restaurantIdOptional   = Optional.ofNullable(context.getRequest().getParameter("restaurantId"));


        if (userIdOptional.isPresent()) {
            var loggedUserId = this.extractRequestUser(jwtAuth);

            if (loggedUserId.equals(UUID.fromString(userIdOptional.get()))) {
                return new AuthorizationDecision(true);

            } else {
                return restaurantIdOptional.isPresent() ? this.checkRestaurantManager(authenticationSupplier, context, "CONSULTAR_PEDIDOS")
                                                        : new AuthorizationDecision(false);
            }
        }

        return restaurantIdOptional.isPresent() ? this.checkRestaurantManager(authenticationSupplier, context, "CONSULTAR_PEDIDOS")
                                                : new AuthorizationDecision(false);

    }

    public AuthorizationDecision checkOrderAccess(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        var authentication = authenticationSupplier.get();

        assert context != null;
        var pathVariables = context.getVariables();

        if ( this.checkIfCanAccessAnyOrderPolicy(authentication) )
            return new AuthorizationDecision(true);

        boolean isOwner = this.checkOrdersOwnerPolicy(authentication, pathVariables.get("orderId"));

        var restaurantIdOptional = Optional.ofNullable(context.getRequest().getParameter("restaurantId"));

        return restaurantIdOptional.isPresent() ? this.checkRestaurantManager(authenticationSupplier, context, "CONSULTAR_PEDIDOS")
                                                :  new AuthorizationDecision(isOwner);
    }

    public AuthorizationDecision checkIfHimself(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        var authentication = authenticationSupplier.get();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return new AuthorizationDecision(false);
        }

        assert context != null;
        var pathVariables = context.getVariables();

        var loggedUserId = this.extractRequestUser(jwtAuth);


        var isHimself = loggedUserId.equals(UUID.fromString(pathVariables.get("userId")));

        return new AuthorizationDecision(isHimself);
    }

    private boolean checkOrdersOwnerPolicy(Authentication authentication, String orderId) {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return false;
        }

        var userId =  extractRequestUser(jwtAuth);

        return orderRepository.existsOrderByIdAndCustomerId(UUID.fromString(orderId), userId);
    }

    public AuthorizationDecision checkIfOrderBelongsToRestaurantPolicy(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext context, String requiredPermission) {
        var authentication = authenticationSupplier.get();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return new AuthorizationDecision(false);
        }

        assert context != null;
        var pathVariables = context.getVariables();

        var userId =  extractRequestUser(jwtAuth);

        var isBelong =  orderRepository.existsOrderByIdAndCustomerId(UUID.fromString(pathVariables.get("orderId")), userId);

        var  hasAuthority = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(requiredPermission));

        return new AuthorizationDecision(isBelong && hasAuthority);
    }

    private boolean checkIfCanAccessAnyOrderPolicy(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("CONSULTAR_TODOS_PEDIDOS"));
    }

    private UUID extractRequestUser(JwtAuthenticationToken jwtAuth) {
        var jwt = jwtAuth.getToken();
        return   UUID.fromString(jwt.getClaimAsString(JwtClaimKeys.USER_ID.getKey()));
    }

    public  AuthorizationResult checkIfCanAccessReports(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        var authentication = authenticationSupplier.get();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return new AuthorizationDecision(false);
        }

        assert context != null;
        var pathVariables = context.getVariables();

        var isAllowed = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("CONSULTAR_RELATORIOS"));

        return isAllowed ? checkIfOrderBelongsToRestaurantPolicy(authenticationSupplier, context, "GERENCIAR_RESTAURANTES")
                        : new AuthorizationDecision(false);


    }
    public  AuthorizationResult checkIfCanAlterPassword(Supplier<? extends Authentication> authenticationSupplier, RequestAuthorizationContext requestAuthorizationContext) {
        var decision = this.checkIfHimself(authenticationSupplier, requestAuthorizationContext);

        if (!decision.isGranted())
            return new AuthorizationDecision(false);
        else {
            var authentication = authenticationSupplier.get();

            if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
                return new AuthorizationDecision(false);
            }

            var isAllowed = authentication.getAuthorities().stream()
                                            .anyMatch(a -> a.getAuthority().equals("EDITAR_SENHA"));

            return new AuthorizationDecision(isAllowed);
        }
    }
}
