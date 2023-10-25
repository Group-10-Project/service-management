package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class service implements  Iservice{


    @Override
    public ResponseServiceDTO getServices(int id) {
        ResponseServiceDTO service = new ResponseServiceDTO();
        service.setId(UUID.randomUUID());
        service.setDescription("Your ID Is : "+ id);
        return service;
    }

    @Override
    public List<ResponseServiceDTO> getAllServices() {
        List<ResponseServiceDTO> services = new ArrayList<>();
        ResponseServiceDTO service = new ResponseServiceDTO();
        service.setId(UUID.randomUUID());
        service.setName("Dummy");
        service.setDescription("Dummy Description");
        services.add(service);
        return services;
    }

    @Override
    public ResponseServiceDTO createService(createServiceDTO serviceData) {
        ResponseServiceDTO service = new ResponseServiceDTO();

        service.setId(UUID.randomUUID());
        service.setName(serviceData.getName());
        service.setDescription(serviceData.getDescription());
        return service;
    }

    @Override
    public ResponseServiceDTO updateService(String id, UpdateServiceDTO serviceInfo) {
        ResponseServiceDTO service = new ResponseServiceDTO();
        service.setDescription(serviceInfo.getDescription());
        service.setName(serviceInfo.getName());
        service.setId(UUID.randomUUID());
        return service;
    }

    @Override
    public Boolean deleteService(String id) {
        return null;
    }
}
