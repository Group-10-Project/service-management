package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;

import java.util.List;

public interface Icategory {

    ResponseCategoryDTO createCategory(createCategoryDTO category);
    updateCategoryDTO updateCategory(String id, updateCategoryDTO category);

    Boolean deleteCategory(String id);
    ResponseCategoryDTO getCategoryById(String id);
    List<ResponseCategoryDTO> getCategories();

}
