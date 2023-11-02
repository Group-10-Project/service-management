package com.review.servicemanagement.helperFunctions;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.models.Address;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.models.ServiceModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class helperFunctions {
    public String sampleUUID = "f51c7673-011a-431b-8d2f-6377d5e6ad00";
    public ResponseServiceDTO helperFunctionToCreateServiceResponse(){

        ResponseServiceDTO serviceResponse = new ResponseServiceDTO();
        serviceResponse.setId(UUID.randomUUID());
        serviceResponse.setCategory(null);
        serviceResponse.setDescription("Test Description");
        serviceResponse.setName("Test Name");
        serviceResponse.setAddress( helperFunctionToCreateAddress());
        return serviceResponse;
    }
    public  createServiceDTO helperFunctionToCreateServiceInput(){
        createServiceDTO serviceResponse = new createServiceDTO();
        serviceResponse.setDescription("Test Description");
        serviceResponse.setName("Test Name");
        serviceResponse.setCategoryId(sampleUUID);
        serviceResponse.setAddress( helperFunctionToCreateAddress());
        return serviceResponse;
    }
    public  Address helperFunctionToCreateAddress(){
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setPincode(123666);
        address.setLocation("IN");
        address.setHouseNumber("123");
        address.setStreetName("Test Street");
        return address;
    }

    public CategoryModel createCategory(){
        CategoryModel category = new CategoryModel();
        category.setId(UUID.fromString(sampleUUID));
        category.setCategoryName("Test Category");
        return category;
    }
    public ServiceModel createServiceModel(){
        ServiceModel service= new ServiceModel();
        service.setName("Test");
        service.setCategory(createCategory() );
        service.setDescription("Description");
        service.setAddress(helperFunctionToCreateAddress());
        service.setId(UUID.randomUUID());
        return service;
    }
    public UpdateServiceDTO UpdateServiceDTO(){
        UpdateServiceDTO service = new UpdateServiceDTO();
        service.setName("Test");
        service.setCategoryId(UUID.randomUUID().toString());
        service.setDescription("Description");
        service.setAddress(helperFunctionToCreateAddress());
        service.setId(UUID.randomUUID().toString());
        return service;
    }
}
