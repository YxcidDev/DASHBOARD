package com.products.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.products.dashboard.model.MembershipModel;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipModel, Long> {

}
