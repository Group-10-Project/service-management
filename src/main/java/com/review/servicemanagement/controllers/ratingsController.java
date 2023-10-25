package com.review.servicemanagement.controllers;

import com.review.servicemanagement.dto.AddRatingsDTO;
import com.review.servicemanagement.dto.ResponseRatingsDTO;
import com.review.servicemanagement.dto.updateRatingsDTO;
import com.review.servicemanagement.services.IRatings;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class ratingsController {

private IRatings ratingService;

public ratingsController(IRatings rating){
    this.ratingService = rating;
}

    @PostMapping
    public ResponseRatingsDTO addRating(@RequestBody AddRatingsDTO ratingsInfo){
    return this.ratingService.addRatings(ratingsInfo);
    }

    @PutMapping("/{ratingID}")
    public ResponseRatingsDTO updateRating(@PathVariable String ratingID,@RequestBody updateRatingsDTO ratingsInfo){
        return this.ratingService.updateRatings(ratingID,ratingsInfo);
    }


}
