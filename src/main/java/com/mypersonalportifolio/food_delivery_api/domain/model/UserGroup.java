package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "user_group")
@Getter
public class UserGroup extends DomainEntityUUID {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_group_permission",
            joinColumns = @JoinColumn (name = "user_group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions = new ArrayList<>();

    public List<Permission> getPermissions() {
        return Collections.unmodifiableList(this.permissions);
    }

    public void setName(String name) {
        this.name = name;
    }
}
