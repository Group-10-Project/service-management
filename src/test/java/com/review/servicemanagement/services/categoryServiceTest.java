package com.review.servicemanagement.services;


import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.exceptions.DuplicateValueException;
import com.review.servicemanagement.models.CategoryModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@ContextConfiguration(classes= ServiceManagementApplicationTests.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class categoryServiceTest {

    String categoryName="Category_1";

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

        //Act and Assert
        assertThrows(DuplicateValueException.class,()-> categoryService.createCategory(categoryCreation));

    }

}
