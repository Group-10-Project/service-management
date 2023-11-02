package com.review.servicemanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
import com.review.servicemanagement.exceptions.DuplicateValueException;
import com.review.servicemanagement.exceptions.NotFoundException;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.services.Icategory;
import jdk.jfr.Category;
import lombok.val;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(categoryController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class categoryControllerWebMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Icategory categoryService;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void createCategory_successful() throws Exception{
        // Arrange
        CategoryModel category = new CategoryModel();
        category.setCategoryName("Hello");
        category.setId(UUID.randomUUID());

        ResponseCategoryDTO categoryResponse= ResponseCategoryDTO.from(category);

        createCategoryDTO categoryDTO = new createCategoryDTO();
        categoryDTO.setCategoryName("Hello");

        when(categoryService.createCategory(any())).thenReturn(categoryResponse);
        //Act and Assert
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO))
                ).andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(categoryResponse)));
    }
    @Test
    void createCategory_unsuccessful_throwsDuplicateCategory() throws Exception{
        // Arrange
        createCategoryDTO categoryDTO = new createCategoryDTO();
        categoryDTO.setCategoryName("Hello");
        DuplicateValueException duplicate = new DuplicateValueException("Duplicate Category Found");

        when(categoryService.createCategory(any())).thenThrow(duplicate);
        //Act and Assert
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO))
                ).andExpect(status().isFound())
                .andExpect(jsonPath("$.code").value("FOUND"))
                .andExpect(jsonPath("$.message").value("Duplicate Category Found"));

    }

   @Test
    void getCategories_verifyIfListOfCategoriesExist() throws Exception {
        CategoryModel category = new CategoryModel();
        category.setCategoryName("Hello");
        category.setId(UUID.randomUUID());
        List<CategoryModel> categories = new ArrayList<>();
        categories.add(category);
        List<ResponseCategoryDTO> categoryResponse= ResponseCategoryDTO.from(categories);
        when(categoryService.getCategories()).thenReturn(categoryResponse);

        mockMvc.perform(get("/category/all"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(categories)));

    }
    @Test
    void getCategories_verifyIfListOfCategoriesisEmpty() throws Exception {
        List<CategoryModel> categories = new ArrayList<>();
        List<ResponseCategoryDTO> categoryResponse= ResponseCategoryDTO.from(categories);
        when(categoryService.getCategories()).thenReturn(categoryResponse);

        mockMvc.perform(get("/category/all"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));

    }

    @Test
    void getCategoryById_verifyIfSuccessful() throws Exception {

        CategoryModel category = new CategoryModel();
        category.setCategoryName("Hello");
        category.setId(UUID.randomUUID());

        ResponseCategoryDTO categoryResponse= ResponseCategoryDTO.from(category);
        when(categoryService.getCategoryById(any())).thenReturn(categoryResponse);

        mockMvc.perform(get("/category/123"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(category)));

    }
    @Test
    void getCategoryById_verifyIfNotFoundExceptionOccurs() throws Exception {

        when(categoryService.getCategoryById(any())).thenThrow( new NotFoundException("Category Not Found"));
        mockMvc.perform(get("/category/123"))
                .andExpect(status().isNotFound())
                //.andExpect(content().string(objectMapper.writeValueAsString("Category Not Found")));
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Category Not Found"));

    }

    @Test
    void deleteCategory_ifSuccessful() throws  Exception{


        when(categoryService.deleteCategory(any())).thenReturn(true);
        mockMvc.perform(delete("/category/123") ).andExpect(status().isOk()).andExpect(content().string("true"));

    }
    @Test
    void deleteCategory_ifCategoryNotFound_throwsNotFoundException() throws  Exception{
        String notFoundText="Category Not Found";
        NotFoundException notFound = new NotFoundException(notFoundText);
        when(categoryService.deleteCategory(any())).thenThrow(notFound);
        mockMvc.perform(delete("/category/123") ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value(notFoundText));

    }

    @Test
    void updateCategory_successfulUpdate() throws Exception{
        CategoryModel updateCategory = new CategoryModel();
        updateCategory.setCategoryName("Updated Name");
        updateCategory.setId(UUID.randomUUID());

        ResponseCategoryDTO response = ResponseCategoryDTO.from(updateCategory);
        when(categoryService.updateCategory(any(),any())).thenReturn(response);
        mockMvc.perform(put("/category/123").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateCategory)))
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }
    @Test
    void updateCategory_unsuccessfulUpdate_throwsNotFoundException() throws Exception{
        CategoryModel updateCategory = new CategoryModel();
        updateCategory.setCategoryName("Updated Name");
        updateCategory.setId(UUID.randomUUID());

        when(categoryService.updateCategory(any(),any())).thenThrow(new NotFoundException("Category Not Found"));
        mockMvc.perform(put("/category/123").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateCategory)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Category Not Found"));
    }

}
