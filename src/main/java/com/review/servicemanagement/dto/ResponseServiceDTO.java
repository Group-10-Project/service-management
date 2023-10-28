package com.review.servicemanagement.dto;

import com.review.servicemanagement.models.Address;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.models.ServiceModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ResponseServiceDTO {
    UUID id;
    String name;
    String description;
    CategoryModel category;
    Address address;

    public static ResponseServiceDTO from(ServiceModel service) {
        ResponseServiceDTO ResponseServiceModel = new ResponseServiceDTO();
        ResponseServiceModel.setName(service.getName());
        ResponseServiceModel.setId(service.getId());
        ResponseServiceModel.setCategory(service.getCategory());
        ResponseServiceModel.setDescription(service.getDescription());
        ResponseServiceModel.setAddress(service.getAddress());
        return ResponseServiceModel;
    }
    public static List<ResponseServiceDTO> from(List<ServiceModel> services) {
        List<ResponseServiceDTO> responseList = new ArrayList<>();

        services.forEach( service -> {
        responseList.add(ResponseServiceDTO.from(service));
        });

        return responseList;
    }
}
