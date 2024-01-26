package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.LoginResponse;
import com.examly.springapp.model.User;
import com.examly.springapp.model.UserResponse;
import com.examly.springapp.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:8081")

public class UserController {
    @Autowired
    UserService userservice;

    @GetMapping("/api/users")
    @CrossOrigin(origins = "http://localhost:8081")
    public List<UserResponse> getalluser() {
        List<User> users = userservice.getAllUser();
        List<UserResponse> responseList = new ArrayList<>();

        for (User user : users) {
            // Assuming you have some logic to get the user details
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String mobileNumber = user.getMobileNumber();
            String email = user.getEmail();
            String role = user.getRole();
            String password = user.getPassword();

            // Create UserResponse without setting the token
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getUserId());
            userResponse.setFirstName(firstName);
            userResponse.setLastName(lastName);
            userResponse.setMobileNumber(mobileNumber);
            userResponse.setEmail(email);
            userResponse.setRole(role);
            userResponse.setPassword(password);

            responseList.add(userResponse);
        }

        return responseList;
    }

}
