package com.review.servicemanagement.dto;

import lombok.Data;

@Data
public class updateRatingsDTO {
    String id;
    String serviceId;
    int ratingValue;
}
