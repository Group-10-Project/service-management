package com.review.servicemanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.models.Address;
import com.review.servicemanagement.services.Iservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockBean
    Iservice service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper  objectMapper;

    @Test
    void getServicesById_success() throws Exception {

        //Arrange
        ResponseServiceDTO serviceResponse = helperFunctionToCreateService();

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

    private ResponseServiceDTO helperFunctionToCreateService(){
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setPincode(123666);
        ResponseServiceDTO serviceResponse = new ResponseServiceDTO();
        serviceResponse.setId(UUID.randomUUID());
        serviceResponse.setCategory(null);
        serviceResponse.setDescription("Test Description");
        serviceResponse.setName("Test Name");
        serviceResponse.setAddress( address);
        return serviceResponse;
    }

}
