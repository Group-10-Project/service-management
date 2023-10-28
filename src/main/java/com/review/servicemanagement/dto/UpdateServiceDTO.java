package com.review.servicemanagement.dto;

import com.review.servicemanagement.models.Address;
import lombok.Data;


@Data
public class UpdateServiceDTO {
    String id;
    String name;
    String description;
    String categoryId;
    Address address;
}
