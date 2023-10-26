package com.review.servicemanagement.repository;

import com.review.servicemanagement.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {

        Optional<CategoryModel> findByCategoryName(String name);
}
