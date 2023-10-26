package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.models.ServiceModel;
import com.review.servicemanagement.repository.CategoryRepository;
import com.review.servicemanagement.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class service implements  Iservice{

    CategoryRepository categoryRepo;
    ServiceRepository serviceRepo;
    Icategory categoryService;

    public service(CategoryRepository repo,ServiceRepository serviceRepo,Icategory CategoryService){
        this.categoryRepo = repo;
        this.serviceRepo = serviceRepo;
        this.categoryService = CategoryService;
    }
    @Override
    public ResponseServiceDTO getServices(String id) {
        Optional<ServiceModel> service = this.serviceRepo.findById(UUID.fromString(id));
        if(service.isEmpty()) return null;
        return ResponseServiceDTO.from(service.get());
    }

    @Override
    public List<ResponseServiceDTO> getAllServices() {

         List<ServiceModel> services =   serviceRepo.findAll();
         return ResponseServiceDTO.from(services);
    }

    @Override
    public ResponseServiceDTO createService(createServiceDTO serviceData) throws Exception {

        ServiceModel serviceModel = new ServiceModel();
            serviceModel.setDescription(serviceData.getDescription());
            serviceModel.setName(serviceData.getName());
            Optional<CategoryModel> category = null;

            if(serviceData.getCategoryId() != null) {
                category = categoryRepo.findById(UUID.fromString(serviceData.getCategoryId()));
            }
            if(category == null ||  category.isEmpty())
            {                category = Optional.ofNullable(this.categoryService.findOrCreateUnListedCategory());
            }
            serviceModel.setCategory(category.get());
            ServiceModel storedServiceModel = serviceRepo.save(serviceModel);
        return ResponseServiceDTO.from(storedServiceModel);
    }

    @Override
    public ResponseServiceDTO updateService(String id, UpdateServiceDTO serviceInfo) throws Exception {

        ServiceModel service = new ServiceModel();
        service.setId(UUID.fromString(serviceInfo.getId()));
        service.setDescription(serviceInfo.getDescription());
        service.setName(serviceInfo.getName());

        Optional<CategoryModel> category =  categoryRepo.findById(UUID.fromString(serviceInfo.getCategoryId()));
        if(category.isEmpty()) {
                throw new Exception("Unable to Find the Category");
        }
        else {
            service.setCategory(category.get());
        }
        ServiceModel storedService = this.serviceRepo.saveAndFlush(service);
        return ResponseServiceDTO.from(storedService);

    }

    @Override
    public Boolean deleteService(String id) throws Exception {
        UUID uuid = UUID.fromString(id);
        Optional<ServiceModel> service =  this.serviceRepo.findById(uuid);
        if(service.isEmpty()){
            throw new Exception("Service Not Found");
        }
        this.serviceRepo.deleteById(uuid);
        return true;
    }
}
