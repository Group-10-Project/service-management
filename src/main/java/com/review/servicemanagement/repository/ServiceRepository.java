package com.review.servicemanagement.repository;

import com.review.servicemanagement.models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceModel, UUID> {

}
