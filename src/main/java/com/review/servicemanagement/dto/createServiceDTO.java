package com.review.servicemanagement.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.review.servicemanagement.models.Address;
import lombok.Builder;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class createServiceDTO {
    String name;
    String description;
    String categoryId;
    Address address;
}
