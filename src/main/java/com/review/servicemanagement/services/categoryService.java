package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
import com.review.servicemanagement.exceptions.DuplicateValueException;
import com.review.servicemanagement.exceptions.NotFoundException;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class categoryService  implements  Icategory{
    CategoryRepository categoryRepository;
    private categoryService(CategoryRepository repository){
        this.categoryRepository = repository;
    }
    @Override
    public ResponseCategoryDTO createCategory(createCategoryDTO category) throws Exception{
        Optional<CategoryModel> categoryOptional =  categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryOptional.isPresent()){
            throw new DuplicateValueException("Duplicate Category Name Found");
        }
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName(category.getCategoryName());
        CategoryModel storedCategory = categoryRepository.save(categoryModel);
        return ResponseCategoryDTO.from(storedCategory);
    }

    @Override
    public ResponseCategoryDTO updateCategory(String id, updateCategoryDTO category) throws Exception {
        Optional<CategoryModel> categoryModel =  this.categoryRepository.findById(UUID.fromString(category.getId()));

        if(categoryModel.isEmpty()) {
            throw new NotFoundException("Category ID Not Found");
        }
        else if(categoryModel.get().getCategoryName().equalsIgnoreCase(category.getCategoryName())){
            throw new DuplicateValueException("Duplicate Category Name Found");
        }

            CategoryModel updatedModel = new CategoryModel();
            updatedModel.setCategoryName(category.getCategoryName());
            updatedModel.setId(UUID.fromString(category.getId()));
            updatedModel = this.categoryRepository.saveAndFlush(updatedModel);

        return ResponseCategoryDTO.from(updatedModel);

    }

    public Boolean deleteCategory(String id) throws NotFoundException {
        UUID uuid = UUID.fromString(id);
        Optional<CategoryModel> category =  this.categoryRepository.findById(uuid);
        if(category.isEmpty()){
            throw new NotFoundException("Category Not Found");
        }
        this.categoryRepository.deleteById(uuid);
        return true;
    }

    public ResponseCategoryDTO getCategoryById(String id) throws Exception {

        Optional<CategoryModel> category =  categoryRepository.findById(UUID.fromString(id));
        if(category.isEmpty()) {
            throw new NotFoundException("Category Not Found");
        }

        return ResponseCategoryDTO.from(category.get());
    }

    @Override
    public List<ResponseCategoryDTO> getCategories() {
        return  ResponseCategoryDTO.from(categoryRepository.findAll());
    }

    public CategoryModel findOrCreateUnListedCategory(){

        CategoryModel category = this.getCategoryByName("UnListed");
        if(category == null) {
            CategoryModel newCategory = new CategoryModel();
            newCategory.setCategoryName("UnListed");
            category =  this.categoryRepository.save(newCategory);
        }
        return category;

    }

    private CategoryModel getCategoryByName(String name){
        Optional<CategoryModel> category = this.categoryRepository.findByCategoryName(name);
        if(category.isEmpty()) return null;
        return category.get();
    }



}
