package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
import com.review.servicemanagement.exceptions.NotFoundException;
import com.review.servicemanagement.models.CategoryModel;

import java.util.List;

public interface Icategory {

    ResponseCategoryDTO createCategory(createCategoryDTO category) throws Exception;
    ResponseCategoryDTO updateCategory(String id, updateCategoryDTO category) throws NotFoundException, Exception;

    Boolean deleteCategory(String id) throws NotFoundException;
    ResponseCategoryDTO getCategoryById(String id) throws NotFoundException, Exception;
    List<ResponseCategoryDTO> getCategories();

    CategoryModel findOrCreateUnListedCategory();


}
