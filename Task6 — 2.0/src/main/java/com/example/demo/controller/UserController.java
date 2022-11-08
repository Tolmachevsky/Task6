package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public String getUserInfo(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "user_table";
    }

    @GetMapping(path = "/admin")
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.findAll());
        return "users_table";
    }

    @GetMapping(path = "/admin/makeAdmin/{id}")
    public String makeAdmin(@PathVariable("id") Integer id) {
        userService.findById(id).makeAdmin();
        User user = userService.findById(id);
        userService.save(user);
        return "redirect:/admin";
    }
}
