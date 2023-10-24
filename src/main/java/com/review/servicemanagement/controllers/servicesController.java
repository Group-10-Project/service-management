package com.review.servicemanagement.controllers;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class servicesController {


    @GetMapping("{id}")
    public ResponseServiceDTO getServices(@PathVariable int id){
        ResponseServiceDTO service = new ResponseServiceDTO();
        service.setId(UUID.randomUUID());
        service.setDescription("Your ID Is : "+ id);
        return service;
    }

    @GetMapping("/all")
    public List<ResponseServiceDTO> getAllServices(){

        List<ResponseServiceDTO> services = new ArrayList<>();
        ResponseServiceDTO service = new ResponseServiceDTO();
        service.setId(UUID.randomUUID());
        service.setName("Dummy");
        service.setDescription("Dummy Description");
        services.add(service);
        return services;
    }




    @PostMapping
    public ResponseServiceDTO createService(@RequestBody createServiceDTO serviceData) {

        ResponseServiceDTO service = new ResponseServiceDTO();

        service.setId(UUID.randomUUID());
        service.setName(serviceData.getName());
        service.setDescription(serviceData.getDescription());
        return service;
    }

    @PutMapping({"/{id}"})
    public ResponseServiceDTO updateService(@PathVariable String id,@RequestBody UpdateServiceDTO serviceInfo){
        ResponseServiceDTO service = new ResponseServiceDTO();
        service.setDescription(serviceInfo.getDescription());
        service.setName(serviceInfo.getName());
        service.setId(UUID.randomUUID());
        return service;
    }

    @DeleteMapping({"/{id}"})
    public Boolean deleteService(@PathVariable String id){
        if(id.equals("123")){ return false;
        }
        return true;
    }



}
