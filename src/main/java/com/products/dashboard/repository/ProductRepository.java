package com.products.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.products.dashboard.model.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
