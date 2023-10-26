package com.review.servicemanagement.dto;

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

    public static ResponseServiceDTO from(ServiceModel service) {
        ResponseServiceDTO ResponseServiceModel = new ResponseServiceDTO();
        ResponseServiceModel.setName(service.getName());
        ResponseServiceModel.setId(service.getId());
        ResponseServiceModel.setCategory(service.getCategory());
        ResponseServiceModel.setDescription(service.getDescription());
        return ResponseServiceModel;
    }
    public static List<ResponseServiceDTO> from(List<ServiceModel> services) {
        List<ResponseServiceDTO> responseList = new ArrayList<>();

        services.forEach( service -> {
        ResponseServiceDTO ResponseServiceModel = new ResponseServiceDTO();
        ResponseServiceModel.setName(service.getName());
        ResponseServiceModel.setId(service.getId());
        ResponseServiceModel.setCategory(service.getCategory());
        ResponseServiceModel.setDescription(service.getDescription());
        responseList.add(ResponseServiceModel);
        });

        return responseList;
    }
}
