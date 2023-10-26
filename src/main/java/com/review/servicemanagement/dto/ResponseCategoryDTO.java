package com.review.servicemanagement.dto;

import com.review.servicemanagement.models.CategoryModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseCategoryDTO {

    String id;
    String categoryName;


    public static ResponseCategoryDTO from(CategoryModel category){
        ResponseCategoryDTO response = new ResponseCategoryDTO();
        response.setId(category.getId().toString());
        response.setCategoryName(category.getCategoryName());
        return response;
    }
    public static List<ResponseCategoryDTO> from(List<CategoryModel> categories){
        List<ResponseCategoryDTO> responseList = new ArrayList<>();

        categories.forEach( category -> {
            responseList.add(ResponseCategoryDTO.from(category));
        });
        return  responseList;
    }

}
