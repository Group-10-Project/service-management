package com.review.servicemanagement.dto;


import lombok.Data;

@Data
public class AddRatingsDTO {
    String serviceId;
    int ratingValue;
}
