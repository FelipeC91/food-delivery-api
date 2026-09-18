package com.mypersonalportifolio.food_delivery_api.domain.concept;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class DomainEntityUUID {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Spring Boot 3 / Jakarta EE 10 native feature
    @JdbcTypeCode(SqlTypes.VARCHAR)                 // Instructs Hibernate 6 to save as a String
    @Column(name = "id", length = 36, updatable = false, nullable = false)
    private UUID id;

    @Override
    public boolean equals(Object o) {
         if (o == null || getClass() != o.getClass()) return false;
         DomainEntityUUID that = (DomainEntityUUID) o;
         return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
         return Objects.hashCode(id);
 }

}
