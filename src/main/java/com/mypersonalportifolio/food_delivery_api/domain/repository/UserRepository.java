package com.mypersonalportifolio.food_delivery_api.domain.repository;

import com.mypersonalportifolio.food_delivery_api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT DISTINCT permission.name " +
            "FROM User u JOIN u.group userGroup JOIN userGroup.permissions permission " +
            "where u.email = :email")
    List<String> findPermissionNamesByUserEmail(@Param("email") String email);
}
