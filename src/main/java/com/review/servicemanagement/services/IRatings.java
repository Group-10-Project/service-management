package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.AddRatingsDTO;
import com.review.servicemanagement.dto.ResponseRatingsDTO;
import com.review.servicemanagement.dto.updateRatingsDTO;

public interface IRatings {
    ResponseRatingsDTO addRatings(AddRatingsDTO ratingsDTO);
    ResponseRatingsDTO updateRatings(String ratingID,updateRatingsDTO ratingsDTO);
}
