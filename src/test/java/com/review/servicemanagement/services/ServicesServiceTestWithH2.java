package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseCategoryDTO;
import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.dto.internal.ServiceQueryParams;
import com.review.servicemanagement.helperFunctions.helperFunctions;
import com.review.servicemanagement.models.ServiceModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class ServicesServiceTestWithH2 {

    @Autowired
    Iservice services;
    @Autowired
    Icategory categoryService;


    helperFunctions helper = new helperFunctions();

    @Test
    void getServiceById_returnsEmpty() throws Exception{

        assertEquals(services.getServiceById(helper.sampleUUID.toString()),null);
    }

    @Test void deleteService_existingService() throws Exception{
        createServiceDTO serviceToBeCreated =  helper.helperFunctionToCreateServiceInput(false);
        ResponseServiceDTO response =   services.createService(serviceToBeCreated);
        assertEquals(services.deleteService(response.getId().toString()),true);
    }
    @Test void deleteService_nonexistingService_throwsNotFound() throws Exception{
        createServiceDTO serviceToBeCreated =  helper.helperFunctionToCreateServiceInput(false);
        ResponseServiceDTO response =   services.createService(serviceToBeCreated);
        assertEquals(services.deleteService(response.getId().toString()),true);
    }

    @Test
    void getServiceById_success() throws Exception{

        //ResponseCategoryDTO categoryResponse=    categoryService.createCategory(helper.createCategoryDTO());
        createServiceDTO serviceToBeCreated =  helper.helperFunctionToCreateServiceInput(false);
        //serviceToBeCreated.setCategoryId(categoryResponse.getId());
        ResponseServiceDTO response =   services.createService(serviceToBeCreated);
        ResponseServiceDTO responseService = services.getServiceById(String.valueOf(response.getId()));
        assertEquals(responseService, response);
    }

    @Test void getAllServices_emptyResponse_success() throws Exception{
        ServiceQueryParams params = helper.createServiceQueryParams(new ArrayList<>());
        List<ResponseServiceDTO> serviceList = services.getAllServices(params);
        assertEquals(serviceList, new ArrayList<>());
    }
    @Test void getAllServices_withServices_success() throws Exception{
        createServiceDTO serviceToBeCreated =  helper.helperFunctionToCreateServiceInput(false);
         ResponseServiceDTO createdService =  services.createService(serviceToBeCreated);

        ServiceQueryParams params = helper.createServiceQueryParams(new ArrayList<>());
        List<ResponseServiceDTO> serviceList = services.getAllServices(params);

        assertEquals(serviceList, List.of(createdService));
    }

    @Test void createService_withoutCategory_success() throws Exception{
        createServiceDTO serviceToBeCreated =  helper.helperFunctionToCreateServiceInput(false);
        ResponseServiceDTO createdService =  services.createService(serviceToBeCreated);

        assertEquals(createdService.getName(),serviceToBeCreated.getName());
        assertEquals(createdService.getAddress().getHouseNumber(),serviceToBeCreated.getAddress().getHouseNumber());
    }
    @Test void createService_withCategory_success() throws Exception{
        createCategoryDTO createCategoryDTO = helper.createCategoryDTO();
        ResponseCategoryDTO categoryStored = categoryService.createCategory(createCategoryDTO);
        createServiceDTO serviceToBeCreated =  helper.helperFunctionToCreateServiceInput(false);
        serviceToBeCreated.setCategoryId(categoryStored.getId());
        ResponseServiceDTO createdService =  services.createService(serviceToBeCreated);

        assertEquals(createdService.getName(),serviceToBeCreated.getName());
        assertEquals(createdService.getAddress().getHouseNumber(),serviceToBeCreated.getAddress().getHouseNumber());
    }
}
