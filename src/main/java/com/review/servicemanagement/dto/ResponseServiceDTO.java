package com.review.servicemanagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseServiceDTO {
    UUID id;
    String name;
    String description;
}
