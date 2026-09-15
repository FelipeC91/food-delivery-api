package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends DomainEntityUUID {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Getter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "user_user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "user_group_id")
    )
    private Set<UserGroup> group = new HashSet<UserGroup>();

    public Set<UserGroup> getUserGroups() {
        return Collections.unmodifiableSet(this.group);
    }

    public boolean joinGroup(UserGroup userGroup) {
        return this.group.add(userGroup);
    }

    public boolean leaveGroup(UserGroup userGroup) {
        return this.group.remove(userGroup);
    }

}
