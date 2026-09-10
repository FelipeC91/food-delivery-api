package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class User extends DomainEntityUUID {

    @Setter
    @Getter
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @Setter
    @Getter
    @NotBlank
    @Column(nullable = false)
    private String password;

    @Getter
    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "user_user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "user_group_id")
    )
    private List<UserGroup> userGroups = new ArrayList<>();

    public List<UserGroup> getUserGroups() {
        return Collections.unmodifiableList(this.userGroups);
    }

}
