package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
import com.review.servicemanagement.models.CategoryModel;

import java.util.List;

public interface Icategory {

    ResponseCategoryDTO createCategory(createCategoryDTO category);
    ResponseCategoryDTO updateCategory(String id, updateCategoryDTO category) throws Exception;

    Boolean deleteCategory(String id) throws Exception;
    ResponseCategoryDTO getCategoryById(String id);
    List<ResponseCategoryDTO> getCategories();

    CategoryModel findOrCreateUnListedCategory();

}
