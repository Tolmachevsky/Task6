package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping(path = "/registration")
    public String crateUserForm(User user) {
        return "add_user";
    }

    @PostMapping(path = "/registration")
    public String addUser(User user) {
        userService.save(user);
        return "redirect:/login";
    }
}
