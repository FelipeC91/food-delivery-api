package com.mypersonalportifolio.food_delivery_api.domain.model;

import com.mypersonalportifolio.food_delivery_api.domain.concept.DomainEntityUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@Entity
public class User extends DomainEntityUUID {

    @Getter
    private String name;

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "user_user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "user_group_id")
    )
    private List<UserGroup> userGroups;

    public List<UserGroup> getUserGroups() {
        return Collections.unmodifiableList(this.userGroups);
    }

}
