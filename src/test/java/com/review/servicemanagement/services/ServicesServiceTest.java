package com.review.servicemanagement.services;


import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.dto.internal.ServiceQueryParams;
import com.review.servicemanagement.exceptions.NotFoundException;
import com.review.servicemanagement.helperFunctions.helperFunctions;
import com.review.servicemanagement.models.ServiceModel;
import com.review.servicemanagement.repository.CategoryRepository;
import com.review.servicemanagement.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class ServicesServiceTest {

    @MockBean
    Icategory CategoryService;
    @MockBean
    ServiceRepository serviceRepo;
    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    Iservice services;

    helperFunctions helper = new helperFunctions();

    @Test
    void getServiceById_unsuccess_returnsEmpty() throws Exception{

        when(serviceRepo.findById(any())).thenReturn(Optional.empty());
       assertEquals(services.getServiceById(UUID.randomUUID().toString()),null);
    }
    @Test
    void getServiceById_success() throws Exception{
        ServiceModel service = new ServiceModel();
        when(serviceRepo.findById(any())).thenReturn(Optional.of(service));

        assertEquals(services.getServiceById(UUID.randomUUID().toString()),ResponseServiceDTO.from(service));
    }

    @Test
    void getServices_withoutFilterOnCategory() throws Exception{
        //Arrange

        ServiceModel service = helper.createServiceModel();
        List<ServiceModel> servicesList = new ArrayList<>();
        servicesList.add(service);

        when(serviceRepo.findAll()).thenReturn(servicesList);

        ServiceQueryParams params =  new ServiceQueryParams();
        //Act
        assertEquals(services.getAllServices(params),ResponseServiceDTO.from(servicesList));
    }
    @Test
    void getServices_withFilterOnCategory() throws Exception{
        //Arrange

        List<ServiceModel> servicesList = new ArrayList<>();
        ServiceModel service = helper.createServiceModel();
        ServiceModel service_2 = helper.createServiceModel();
        servicesList.add(service);
        servicesList.add(service_2);

        when(serviceRepo.findByCategory_IdIn(any())).thenReturn(servicesList);

        ServiceQueryParams params =  new ServiceQueryParams();
        List<String> ids = new ArrayList<>();
        ids.add(helper.sampleUUID);
        //ids.add(service.getCategory().getId().toString());
        /*ids.add(service_2.getCategory().getId().toString());*/
        params.setCategoryIds(ids);
        //Act
        assertEquals(services.getAllServices(params),ResponseServiceDTO.from(servicesList));
    }

    @Test
    void createService_success() throws Exception{
        createServiceDTO service = helper.helperFunctionToCreateServiceInput();
        ServiceModel serviceModel = helper.createServiceModel();

        when(serviceRepo.save(any())).thenReturn(serviceModel);
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(serviceModel.getCategory()));

        assertEquals(services.createService(service),ResponseServiceDTO.from(serviceModel));

    }
    @Test
    void createService_CreatesNewCategorysuccess() throws Exception{
        createServiceDTO service = helper.helperFunctionToCreateServiceInput();
        ServiceModel serviceModel = helper.createServiceModel();

        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(CategoryService.findOrCreateUnListedCategory()).thenReturn(serviceModel.getCategory());
        when(serviceRepo.save(any())).thenReturn(serviceModel);

        assertEquals(services.createService(service),ResponseServiceDTO.from(serviceModel));

    }

    @Test
    void deleteService_success() throws Exception{
        //Arrange
        when(serviceRepo.findById(any())).thenReturn(Optional.ofNullable(helper.createServiceModel()));
        // Act and Assert
        assertEquals(services.deleteService(helper.sampleUUID),true);
    }
    @Test
    void deleteService_notFoundException() throws Exception{
        //Arrange
        when(serviceRepo.findById(any())).thenReturn(Optional.ofNullable(null));
        // Act and Assert
        assertThrows(NotFoundException.class,()-> services.deleteService(helper.sampleUUID));
    }

    @Test void updateService_throwsNotFoundException() throws Exception{
        //Arrange
        when(serviceRepo.findById(any())).thenReturn(Optional.ofNullable(null));
        //Assert
        assertThrows(NotFoundException.class , ()-> services.updateService(helper.sampleUUID,helper.UpdateServiceDTO()));
    }
    @Test void updateService_throwsServiceNotFoundException() throws Exception{
        //Arrange
        when(serviceRepo.findById(any())).thenReturn(Optional.ofNullable(null));
        //Assert
        assertThrows(NotFoundException.class , ()-> services.updateService(helper.sampleUUID,helper.UpdateServiceDTO()));
    }
    @Test void updateService_throwsCategoryNotFoundException() throws Exception{
        //Arrange
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(serviceRepo.findById(any())).thenReturn(Optional.ofNullable(helper.createServiceModel()));
        //Assert
        assertThrows(NotFoundException.class , ()-> services.updateService(helper.sampleUUID,helper.UpdateServiceDTO()));
    }
    @Test void updateService_successfulUpdate() throws Exception{
        //Arrange
        ServiceModel service = helper.createServiceModel();
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(service.getCategory()));
        when(serviceRepo.findById(any())).thenReturn(Optional.ofNullable(service));
        when(serviceRepo.save(any())).thenReturn(service);
        //Assert
        assertEquals(services.updateService(helper.sampleUUID,helper.UpdateServiceDTO()),ResponseServiceDTO.from(service));
    }
}
