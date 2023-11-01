package com.review.servicemanagement.dto;

import com.review.servicemanagement.models.CategoryModel;
import lombok.Data;

@Data
public class updateCategoryDTO {
    String id;
    String categoryName;
    public static updateCategoryDTO from(CategoryModel category){
        updateCategoryDTO response = new updateCategoryDTO();
        response.setId(category.getId().toString());
        response.setCategoryName(category.getCategoryName());
        return response;
    }
}
