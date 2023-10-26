package com.review.servicemanagement.dto;

import lombok.Data;


@Data
public class UpdateServiceDTO {
    String id;
    String name;
    String description;
    String categoryId;
}
