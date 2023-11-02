package com.review.servicemanagement.services;


import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.updateCategoryDTO;
import com.review.servicemanagement.exceptions.DuplicateValueException;
import com.review.servicemanagement.exceptions.NotFoundException;
import com.review.servicemanagement.models.CategoryModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@ContextConfiguration(classes= ServiceManagementApplicationTests.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class categoryServiceTest {

    String categoryName="Category_1";
    String sampleCategoryId = "f51c7673-011a-431b-8d2f-6377d5e6ad00";

    @Autowired
    Icategory categoryService;

    @Test
    void createCategory_Successful() throws Exception {

        //Arrange
        createCategoryDTO categoryCreation = new createCategoryDTO();
        categoryCreation.setCategoryName(categoryName);

        //Act
        ResponseCategoryDTO category =  categoryService.createCategory(categoryCreation);

        //Assert
        assertEquals(categoryName,category.getCategoryName());

    }

    @Test
    void createCategory_duplicateCategoryCreation_throwsDuplicateError() throws Exception {

        //Arrange
        createCategoryDTO categoryCreation = new createCategoryDTO();
        categoryCreation.setCategoryName(categoryName);
        categoryService.createCategory(categoryCreation);

        //Act and Assert
        assertThrows(DuplicateValueException.class,()-> categoryService.createCategory(categoryCreation));

    }

    @Test
    void deleteCategory_Success() throws Exception{

        //Arrange
        createCategoryDTO categoryCreation = new createCategoryDTO();
        categoryCreation.setCategoryName(categoryName);
        ResponseCategoryDTO category =  categoryService.createCategory(categoryCreation);

        //Act and Assert
        Boolean deletedRecord = categoryService.deleteCategory(category.getId());
        assertEquals(deletedRecord,true);

    }
    @Test
    void deleteCategory_UnSuccess_throwsException() throws Exception{

        //Act and Assert
        assertThrows(IllegalArgumentException.class,()-> categoryService.deleteCategory("1231231231231"));
        assertThrows(NotFoundException.class,()-> categoryService.deleteCategory(sampleCategoryId));

    }

    @Test
    void getCategoryById_success() throws Exception{
        //Arrange
        createCategoryDTO categoryCreation = new createCategoryDTO();
        categoryCreation.setCategoryName(categoryName);
        ResponseCategoryDTO category =  categoryService.createCategory(categoryCreation);

        //Act
        ResponseCategoryDTO responseCategory = categoryService.getCategoryById(category.getId());
        assertEquals(responseCategory,category);
    }
    @Test
    void getCategoryById_unsuccess_throwsNotFoundException() throws Exception{

        assertThrows(NotFoundException.class,()-> categoryService.getCategoryById(sampleCategoryId));
    }

    @Test
    void getAllCategories_whenEmpty() throws Exception{
        List<ResponseCategoryDTO> categories = categoryService.getCategories();
        assertEquals("[]",categories.toString());
    }

    @Test
    void getAllCategories_withData() throws Exception{
        //Arrange
        List<ResponseCategoryDTO> categories = new ArrayList<>();
        createCategoryDTO categoryCreation = new createCategoryDTO();
        categoryCreation.setCategoryName(categoryName);
        ResponseCategoryDTO category =  categoryService.createCategory(categoryCreation);
        categoryCreation.setCategoryName("Test");
        ResponseCategoryDTO category_1 =  categoryService.createCategory(categoryCreation);
        categories.add(category);
        categories.add(category_1);
        //Act
        List<ResponseCategoryDTO> categoriesResponse = categoryService.getCategories();

        //Assert
        assertEquals(categories,categoriesResponse);
    }

    @Test
    void findOrCreateUnListedCategory(){

        // Act
        CategoryModel category = categoryService.findOrCreateUnListedCategory();
        CategoryModel categoryFound = categoryService.findOrCreateUnListedCategory();

        //Assert
        assertEquals(categoryFound.toString(),category.toString());
    }

    @Test
    void updateCategory_success() throws Exception {

        //Arrange
        String updatedCategoryName  = "Updated_Category_Name";
        //Arrange
        createCategoryDTO categoryCreation = new createCategoryDTO();
        categoryCreation.setCategoryName(categoryName);
        ResponseCategoryDTO category =  categoryService.createCategory(categoryCreation);
        //Act
        updateCategoryDTO updatedCategory = new updateCategoryDTO();
        updatedCategory.setCategoryName(updatedCategoryName);
        updatedCategory.setId(category.getId());
        ResponseCategoryDTO categoryUpdated = categoryService.updateCategory(category.getId().toString(),updatedCategory);

        assertEquals(categoryUpdated.getCategoryName(),updatedCategoryName);
        assertEquals(categoryUpdated.getId(),category.getId());
    }
    @Test
    void updateCategory_UnSuccessful_throwsNotFoundException() throws  Exception{
        updateCategoryDTO updatedCategory = new updateCategoryDTO();
        updatedCategory.setId(sampleCategoryId);
        updatedCategory.setCategoryName("test-tass");
        assertThrows(NotFoundException.class,()-> categoryService.updateCategory(sampleCategoryId,updatedCategory));
        //assertEquals(categoryService.updateCategory(sampleCategoryId,updatedCategory).toString(),"Category ID Not Found");
    }
    @Test
    void updateCategory_UnSuccessful_throwsDuplicateException() throws  Exception{

        //Arrange

        String updatedCategoryName  = "Updated_Category_Name";
        createCategoryDTO categoryCreation_1 = new createCategoryDTO();
        categoryCreation_1.setCategoryName(updatedCategoryName);
        ResponseCategoryDTO category_1 =  categoryService.createCategory(categoryCreation_1);

        createCategoryDTO categoryCreation = new createCategoryDTO();
        categoryCreation.setCategoryName(categoryName);
        ResponseCategoryDTO category =  categoryService.createCategory(categoryCreation);



        updateCategoryDTO updateCategory = new updateCategoryDTO();
        updateCategory.setCategoryName(updatedCategoryName);
        updateCategory.setId(category.getId());
        //Act and Assert
        assertThrows(DuplicateValueException.class,()-> categoryService.updateCategory(category.getId(),updateCategory));
    }
}
