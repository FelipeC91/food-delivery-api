package com.mypersonalportifolio.food_delivery_auth_server.infraestructure.jpa;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
public class CustomUserDetails extends User {

    private final  UUID id;
    private final  String fullUsername;

    public CustomUserDetails(com.mypersonalportifolio.food_delivery_auth_server.domain.model.User user,
                             Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.id = user.getId();
        this.fullUsername = user.getName();
    }
}
