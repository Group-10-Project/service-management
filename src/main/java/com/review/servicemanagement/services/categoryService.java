package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
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
    public ResponseCategoryDTO createCategory(createCategoryDTO category) {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName(category.getCategoryName());
        CategoryModel storedCategory = categoryRepository.save(categoryModel);
        return ResponseCategoryDTO.from(storedCategory);
    }

    @Override
    public ResponseCategoryDTO updateCategory(String id, updateCategoryDTO category) throws Exception {
        Optional<CategoryModel> categoryModel =  this.categoryRepository.findById(UUID.fromString(category.getId()));

        if(categoryModel.isEmpty()) {
            throw new Exception("Category ID Not Found");
        }

            CategoryModel updatedModel = new CategoryModel();
            updatedModel.setCategoryName(category.getCategoryName());
            updatedModel.setId(UUID.fromString(category.getId()));
            updatedModel = this.categoryRepository.saveAndFlush(updatedModel);

        return ResponseCategoryDTO.from(updatedModel);

    }

    public Boolean deleteCategory(String id) throws Exception {
        UUID uuid = UUID.fromString(id);
        Optional<CategoryModel> category =  this.categoryRepository.findById(uuid);
        if(category.isEmpty()){
            throw new Exception("Category Not Found");
        }
        this.categoryRepository.deleteById(uuid);
        return true;
    }

    public ResponseCategoryDTO getCategoryById(String id){

        Optional<CategoryModel> category =  categoryRepository.findById(UUID.fromString(id));
        return category.map(ResponseCategoryDTO::from).orElse(null);
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
