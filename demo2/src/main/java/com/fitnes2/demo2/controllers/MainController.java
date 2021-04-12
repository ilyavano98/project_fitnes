package com.fitnes2.demo2.controllers;

import com.fitnes2.demo2.model.Role;
import com.fitnes2.demo2.repositoryes.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/")
    public String home( Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/home")
    public String home_1( Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/login")
    public String Login(Model model) {
            return "login";
        }

}
