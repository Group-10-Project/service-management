package com.review.servicemanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.dto.internal.ServiceQueryParams;
import com.review.servicemanagement.exceptions.NotFoundException;
import com.review.servicemanagement.models.Address;
import com.review.servicemanagement.services.Iservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(servicesController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class servicesControllerWebMVCTest {
    String sampleUUID = "f51c7673-011a-431b-8d2f-6377d5e6ad00";
    @MockBean
    Iservice service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper  objectMapper;

    @Test
    void getServicesById_success() throws Exception {

        //Arrange
        ResponseServiceDTO serviceResponse = helperFunctionToCreateServiceResponse();

        when(service.getServiceById(any())).thenReturn(serviceResponse);

        //Act and Assert
        mockMvc.perform(get("/services/123")).andExpect(
                status().isOk()
        ).andExpect(content().string(objectMapper.writeValueAsString(serviceResponse)));
    }

    @Test
    void getServiceById_returnsNullWhenNotFound() throws Exception{
        //Arrange
        when(service.getServiceById(any())).thenReturn(null);
        //Act and Assert
        mockMvc.perform(get("/services/123")).andExpect(
                status().isOk()
        ).andExpect(content().string(""));
    }

    @Test
    void getAllServices_returnsList() throws Exception{
        //Arrange
        ServiceQueryParams param = new ServiceQueryParams();
        List<ResponseServiceDTO> services = new ArrayList<>();
        when(service.getAllServices(param)).thenReturn(services);

        //Act and Assert
        mockMvc.perform(get("/services/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(services)));
    }

    @Test
    void createService_success() throws Exception{
        //Arrange
            createServiceDTO serviceObject = helperFunctionToCreateServiceInput();
            ResponseServiceDTO serviceResponse = helperFunctionToCreateServiceResponse();
            when(service.createService(any())).thenReturn(serviceResponse);

        //Act
        mockMvc.perform(post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceObject)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(serviceResponse)));
    }

    @Test
    void updateService_success() throws  Exception{
        ResponseServiceDTO response = helperFunctionToCreateServiceResponse();
        when(service.updateService(any(),any())).thenReturn(response);
        mockMvc.perform(put("/services/"+sampleUUID).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UpdateServiceDTO()))).andExpect(
                        status().isOk()        )
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void updateService_unsuccessful_throwsNotFoundException() throws  Exception{
        ResponseServiceDTO response = helperFunctionToCreateServiceResponse();
        when(service.updateService(any(),any())).thenThrow(new NotFoundException("Service Not Found"));
        mockMvc.perform(put("/services/"+sampleUUID).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateServiceDTO()))).andExpect(
                        status().isNotFound()        )
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Service Not Found"));
    }
    @Test
    void updateService_unsuccessful_throwsCategoryNotFoundException() throws  Exception{
        ResponseServiceDTO response = helperFunctionToCreateServiceResponse();
        when(service.updateService(any(),any())).thenThrow(new NotFoundException("Unable to Find the Category"));
        mockMvc.perform(put("/services/"+sampleUUID).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateServiceDTO()))).andExpect(
                        status().isNotFound()        )
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Unable to Find the Category"));
    }

    @Test
    void deleteService_successful() throws Exception{

        //Arrange
        when(service.deleteService(any())).thenReturn(true);
        //Assert
        mockMvc.perform(delete("/services/123")).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
    @Test
    void deleteService_unsuccessful_throwsServiceNotFoundException() throws Exception{

        //Arrange
        when(service.deleteService(any())).thenThrow(new Exception("Service Not Found"));
        //Assert
        mockMvc.perform(delete("/services/123"))
                .andExpect(jsonPath("$.code").value("NOT_ACCEPTABLE"))
                .andExpect(jsonPath("$.message").value("Service Not Found"));
    }

    private ResponseServiceDTO helperFunctionToCreateServiceResponse(){

        ResponseServiceDTO serviceResponse = new ResponseServiceDTO();
        serviceResponse.setId(UUID.randomUUID());
        serviceResponse.setCategory(null);
        serviceResponse.setDescription("Test Description");
        serviceResponse.setName("Test Name");
        serviceResponse.setAddress( helperFunctionToCreateAddress());
        return serviceResponse;
    }
    private createServiceDTO helperFunctionToCreateServiceInput(){
        createServiceDTO serviceResponse = new createServiceDTO();
        serviceResponse.setDescription("Test Description");
        serviceResponse.setName("Test Name");
        serviceResponse.setCategoryId(sampleUUID);
        serviceResponse.setAddress( helperFunctionToCreateAddress());
        return serviceResponse;
    }
    private Address helperFunctionToCreateAddress(){
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setPincode(123666);
        address.setLocation("IN");
        address.setHouseNumber("123");
        address.setStreetName("Test Street");
        return address;
    }
}
