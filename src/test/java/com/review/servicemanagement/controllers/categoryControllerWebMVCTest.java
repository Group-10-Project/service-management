package com.review.servicemanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.services.Icategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(categoryController.class)
public class categoryControllerWebMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Icategory categoryService;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    void verifyWhenThereisNoListOfCategories() throws Exception {
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/category/all"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));

    }

    @Test
    void verifyListOfCategories() throws Exception {
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

}
