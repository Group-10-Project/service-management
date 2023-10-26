package com.review.servicemanagement.dto;

import com.review.servicemanagement.models.CategoryModel;
import lombok.Data;

@Data
public class ResponseCategoryDTO {

    String id;
    String categoryName;
    int ratingsValue;

    public static ResponseCategoryDTO from(CategoryModel category){
        ResponseCategoryDTO response = new ResponseCategoryDTO();
        response.setId(category.getId().toString());
        response.setCategoryName(category.getCategoryName());
        return response;
    }

}
