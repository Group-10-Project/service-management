package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class categoryService  implements  Icategory{


    @Override
    public ResponseCategoryDTO createCategory(createCategoryDTO category) {
        ResponseCategoryDTO categoryDTO = new ResponseCategoryDTO();
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setId(UUID.randomUUID().toString());
        return categoryDTO;
    }

    @Override
    public updateCategoryDTO updateCategory(String id,updateCategoryDTO category) {
        updateCategoryDTO categoryDTO = new updateCategoryDTO();
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setId(UUID.randomUUID().toString());
        return categoryDTO;
    }

    public Boolean deleteCategory(String id){
        return true;
    }

    public ResponseCategoryDTO getCategoryById(String id){
        ResponseCategoryDTO categoryDTO = new ResponseCategoryDTO();
        categoryDTO.setCategoryName("Dummy");
        categoryDTO.setId(UUID.randomUUID().toString());
        return categoryDTO;
    }

    @Override
    public List<ResponseCategoryDTO> getCategories() {

        List<ResponseCategoryDTO> categoryDTOList = new ArrayList<>();

        ResponseCategoryDTO categoryDTO = new ResponseCategoryDTO();
        categoryDTO.setCategoryName("Dummy");
        categoryDTO.setId(UUID.randomUUID().toString());
        categoryDTOList.add(categoryDTO);

        return categoryDTOList;
    }


}
