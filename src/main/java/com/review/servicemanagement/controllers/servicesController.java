package com.review.servicemanagement.controllers;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.dto.internal.ServiceQueryParams;
import com.review.servicemanagement.services.Iservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class servicesController {
    private Iservice service;
    public servicesController(Iservice services){
        this.service = services;
    }

    @GetMapping("{id}")
    public ResponseServiceDTO getServices(@PathVariable String id){
        return this.service.getServiceById(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Gets All Services")
    public List<ResponseServiceDTO> getAllServices(@RequestBody(required = false) ServiceQueryParams params){

        return this.service.getAllServices(params);
    }




    @PostMapping
    public ResponseServiceDTO createService(@RequestBody createServiceDTO serviceData) throws Exception {
        return this.service.createService(serviceData);

    }

    @PutMapping({"/{id}"})
    public ResponseServiceDTO updateService (@PathVariable String id,@RequestBody UpdateServiceDTO serviceInfo)  throws Exception{
        return this.service.updateService(id,serviceInfo);
    }

    @DeleteMapping({"/{id}"})
    public Boolean deleteService(@PathVariable String id) throws Exception {
        return this.service.deleteService(id);
    }


}
