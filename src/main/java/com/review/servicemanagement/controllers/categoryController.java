package com.review.servicemanagement.controllers;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
import com.review.servicemanagement.services.Icategory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class categoryController {

    private Icategory categoryService;
    public categoryController(Icategory categoryService){
        this.categoryService = categoryService;
    }
    @PostMapping
    public ResponseCategoryDTO createCategory(@RequestBody createCategoryDTO category){
            return this.categoryService.createCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseCategoryDTO getCategoryById(@PathVariable  String id){
        return this.categoryService.getCategoryById(id);
    }
    @GetMapping("/all")
    public List<ResponseCategoryDTO> getCategories(){
        return this.categoryService.getCategories();
    }

    @DeleteMapping("/{id}")
    public Boolean deleteCategory(@PathVariable String id){
        return this.categoryService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    public updateCategoryDTO updateCategory(@PathVariable String id, @RequestBody updateCategoryDTO category ){
        return this.categoryService.updateCategory(id,category);
    }




}
