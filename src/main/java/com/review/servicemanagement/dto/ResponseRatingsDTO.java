package com.review.servicemanagement.dto;

import lombok.Data;

@Data
public class ResponseRatingsDTO {
    String id;
    String serviceId;
    int ratingValue;
}
