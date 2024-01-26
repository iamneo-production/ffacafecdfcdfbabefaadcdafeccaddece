package com.examly.springapp.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.ApiResponse;
import com.examly.springapp.model.LoginResponse;
import com.examly.springapp.model.User;
import com.examly.springapp.security.JwtUtil;
import com.examly.springapp.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")

public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")

    public ApiResponse registerUser(@RequestBody User user) {
        if (userService.registerUser(user)) {
            return new ApiResponse("success");
        } else {
            return new ApiResponse("Failed to register user");
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> handler2(@RequestBody User user) throws Exception {
        try {
            this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Bad credentials");
        }

        UserDetails userDetails = this.userService.loadUserByUsername(user.getEmail());
        User loggedInUser = userService.getUserByEmail(user.getEmail());

        if (loggedInUser != null) {
            String token = jwtUtil.generateToken(userDetails);

            // Create a custom response object with specific fields
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(loggedInUser.getUserId());
            loginResponse.setFirstName(loggedInUser.getFirstName());
            loginResponse.setLastName(loggedInUser.getLastName());
            loginResponse.setRole(loggedInUser.getRole());
            loginResponse.setToken(token);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("User not found with email: " + user.getEmail());
        }
    }

}