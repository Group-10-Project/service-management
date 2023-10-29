package com.review.servicemanagement.repository;

import com.review.servicemanagement.dto.ResponseServiceDTO;
import com.review.servicemanagement.models.CategoryModel;
import com.review.servicemanagement.models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceModel, UUID> {

    Optional<ResponseServiceDTO> findByIdAndAddress_Id(UUID serviceId,UUID addressId );
    List<ServiceModel> findByCategory_IdIn(List<UUID> categoriesID);

}
