package com.products.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.products.dashboard.model.LoginModel;
import com.products.dashboard.repository.LoginRepository;

@Controller
public class DashboardController {

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    

    @PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String pass, Model model) {
        LoginModel login = loginRepository.findByUsuarioAndPass(usuario, pass);

        if (login != null) {

            return "dashboard";

        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

}
