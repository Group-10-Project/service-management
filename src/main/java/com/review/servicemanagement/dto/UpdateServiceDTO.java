package com.review.servicemanagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateServiceDTO {
    String id;
    String name;
    String description;
    String categoryId;
}
