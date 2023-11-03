package com.review.servicemanagement.helperFunctions;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createCategoryDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.dto.internal.ServiceQueryParams;
import com.review.servicemanagement.models.Address;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.models.ServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;
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
        serviceResponse.setAddress( helperFunctionToCreateAddress(true));
        return serviceResponse;
    }
    public  createServiceDTO helperFunctionToCreateServiceInput(){
        createServiceDTO serviceResponse = new createServiceDTO();
        serviceResponse.setDescription("Test Description");
        serviceResponse.setName("Test Name");
        serviceResponse.setCategoryId(sampleUUID);
        serviceResponse.setAddress( helperFunctionToCreateAddress(false));
        return serviceResponse;
    }
    public  Address helperFunctionToCreateAddress(Boolean includeId){
        Address address = new Address();
        if(includeId) {address.setId(UUID.randomUUID());}
        address.setPincode(123666);
        address.setLocation("IN");
        address.setHouseNumber("123");
        address.setStreetName("Test Street");
        return address;
    }

    public CategoryModel createCategory(Boolean includeId){
        CategoryModel category = new CategoryModel();
        if(includeId) { category.setId(UUID.fromString(sampleUUID)); }
        category.setCategoryName("Test Category");
        return category;
    }
    public createCategoryDTO createCategoryDTO(){
        createCategoryDTO category = new createCategoryDTO();
        category.setCategoryName("Test Category");
        return category;
    }

    public ServiceModel createServiceModel(){
        ServiceModel service= new ServiceModel();
        service.setName("Test");
        service.setCategory(createCategory(false) );
        service.setDescription("Description");
        service.setAddress(helperFunctionToCreateAddress(false));
        service.setId(UUID.randomUUID());
        return service;
    }
    public UpdateServiceDTO UpdateServiceDTO(){
        UpdateServiceDTO service = new UpdateServiceDTO();
        service.setName("Test");
        service.setCategoryId(UUID.randomUUID().toString());
        service.setDescription("Description");
        service.setAddress(helperFunctionToCreateAddress(true));
        service.setId(UUID.randomUUID().toString());
        return service;
    }

    public ServiceQueryParams createServiceQueryParams(List<String> categoryIds){
        ServiceQueryParams query =  new ServiceQueryParams();
            if(!categoryIds.isEmpty()) {
                query.setCategoryIds(categoryIds);
            }
        return query;
    }

}
