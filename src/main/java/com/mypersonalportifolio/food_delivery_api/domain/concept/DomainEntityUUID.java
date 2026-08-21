package com.mypersonalportifolio.food_delivery_api.domain.concept;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass

 @NoArgsConstructor
 @AllArgsConstructor
 @Getter
 @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class DomainEntityUUID {


@Id
@GeneratedValue(strategy = GenerationType.UUID) // Spring Boot 3 / Jakarta EE 10 native feature
@JdbcTypeCode(SqlTypes.VARCHAR)                 // Instructs Hibernate 6 to save as a String
@Column(name = "id", length = 36, updatable = false, nullable = false)
@EqualsAndHashCode.Include
private UUID id;


 @Override
 public boolean equals(Object o) {
  if (o == null || getClass() != o.getClass()) return false;
  DomainEntityUUID that = (DomainEntityUUID) o;
  return Objects.equals(id, that.id);
 }
}
