package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {
    @Autowired
    UserService userservice;

    @GetMapping("/api/users")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<User> getalluser() {
        return userservice.getAllUser();
    }
}
