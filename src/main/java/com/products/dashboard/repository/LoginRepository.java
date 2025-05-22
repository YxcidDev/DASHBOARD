package com.products.dashboard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.products.dashboard.model.LoginModel;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, Long> {
   LoginModel findByUsuarioAndPass(String usuario, String pass);

}
