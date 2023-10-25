package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.AddRatingsDTO;
import com.review.servicemanagement.dto.ResponseRatingsDTO;
import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.updateRatingsDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ratingsService implements  IRatings{

    @Override
    public ResponseRatingsDTO addRatings(AddRatingsDTO ratingsDTO) {
        ResponseRatingsDTO response = new ResponseRatingsDTO();
        response.setServiceId(ratingsDTO.getServiceId());
        response.setRatingValue(ratingsDTO.getRatingValue());
        response.setId(UUID.randomUUID().toString());
        return response;
    }

    @Override
    public ResponseRatingsDTO updateRatings(String ratingID,updateRatingsDTO ratingsDTO) {
        ResponseRatingsDTO response = new ResponseRatingsDTO();
        response.setServiceId(ratingsDTO.getServiceId());
        response.setRatingValue(ratingsDTO.getRatingValue());
        response.setId(UUID.randomUUID().toString());
        return response;
    }
}
