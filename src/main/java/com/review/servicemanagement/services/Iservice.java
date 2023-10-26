package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface Iservice {
    ResponseServiceDTO getServices(@PathVariable String id);
    List<ResponseServiceDTO> getAllServices();
    ResponseServiceDTO createService(@RequestBody createServiceDTO serviceData) throws Exception;
    ResponseServiceDTO updateService(@PathVariable String id,@RequestBody UpdateServiceDTO serviceInfo) throws Exception;
    Boolean deleteService(@PathVariable String id) throws Exception;

}
