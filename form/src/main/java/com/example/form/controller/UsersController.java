package com.example.form.controller;

import com.example.form.model.UsersModel;
import com.example.form.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class UsersController {

    @Autowired
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UsersModel());
        return "login_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("LoginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel){
        System.out.println("register request " + usersModel);
        UsersModel registeredUser = usersService.registeredUsers(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail());
        return registeredUser == null ? "login_page" : "redirect/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model){
        System.out.println("login request " + usersModel);
        UsersModel authenticated = usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if(authenticated != null) {
            model.addAttribute("UserLogin", authenticated.getLogin());
            return "index";
        } else {
            return "login_page";
        }
    }
}
