package com.review.servicemanagement.services;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.dto.UpdateServiceDTO;
import com.review.servicemanagement.dto.createServiceDTO;
import com.review.servicemanagement.dto.internal.ServiceQueryParams;
import com.review.servicemanagement.exceptions.NotFoundException;
import com.review.servicemanagement.models.Address;
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
    public ResponseServiceDTO getServiceById(String id) {
        Optional<ServiceModel> service = this.serviceRepo.findById(UUID.fromString(id));
        if(service.isEmpty()) return null;
        return ResponseServiceDTO.from(service.get());
    }

    @Override
    public List<ResponseServiceDTO> getAllServices(ServiceQueryParams params) {

        List<ServiceModel> services;

        if(params != null && params.getCategoryIds() != null){
            List<UUID> uuids = new ArrayList<>();
            params.getCategoryIds().forEach( id -> uuids.add(UUID.fromString(id)));
            //uuids.add(UUID.fromString(params.getCategoryIds()));
            services = serviceRepo.findByCategory_IdIn( uuids);

        }
        else {
            services = serviceRepo.findAll();
        }

         return ResponseServiceDTO.from(services);
    }

    @Override
    public ResponseServiceDTO createService(createServiceDTO serviceData){

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

        Address address = new Address();
        address.setPincode(serviceData.getAddress().getPincode());
        address.setLocation(serviceData.getAddress().getLocation());
        address.setStreetName(serviceData.getAddress().getStreetName());
        address.setHouseNumber(serviceData.getAddress().getHouseNumber());
        serviceModel.setAddress(address);

            ServiceModel storedServiceModel = serviceRepo.save(serviceModel);
        return ResponseServiceDTO.from(storedServiceModel);
    }

    @Override
    public ResponseServiceDTO updateService(String id, UpdateServiceDTO serviceInfo) throws NotFoundException {

        Optional<ServiceModel> serviceOptional = serviceRepo.findById(UUID.fromString(id));
        if (serviceOptional.isEmpty()) {

        throw new NotFoundException("Service Not Found");
            }

        ServiceModel service = serviceOptional.get();
        service.setId(UUID.fromString(serviceInfo.getId()));
        service.setDescription(serviceInfo.getDescription());
        service.setName(serviceInfo.getName());


        Optional<CategoryModel> category =  categoryRepo.findById(UUID.fromString(serviceInfo.getCategoryId()));
        if(category.isEmpty()) {
            throw new NotFoundException("Unable to Find the Category");
        }
        else {
            CategoryModel categoryModel = category.get();
            service.setCategory(categoryModel);
        }

        Address address = serviceInfo.getAddress();
        address.setId(serviceInfo.getAddress().getId());
        address.setPincode(serviceInfo.getAddress().getPincode());
        address.setLocation(serviceInfo.getAddress().getLocation());
        address.setStreetName(serviceInfo.getAddress().getStreetName());
        address.setHouseNumber(serviceInfo.getAddress().getHouseNumber());
        service.setAddress(address);

        ServiceModel storedService = this.serviceRepo.save(service);
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
