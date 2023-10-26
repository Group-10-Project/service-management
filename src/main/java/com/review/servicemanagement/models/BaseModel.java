package com.review.servicemanagement.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@MappedSuperclass
public class BaseModel {
 @Id
@GeneratedValue(strategy = GenerationType.UUID)
 @Column(name = "id", columnDefinition = "BINARY(16)")
 private UUID id;
}
