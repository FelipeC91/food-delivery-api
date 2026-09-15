package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity(name = "user_group")

public class UserGroup extends DomainEntityUUID {

    @NotBlank
    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_group_permission",
            joinColumns = @JoinColumn (name = "user_group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public boolean attachPermission(Permission permission) {
        return permissions.add(permission);
    }

    public boolean detachPermission(Permission permission) {
        return permissions.remove(permission);
    }

}
