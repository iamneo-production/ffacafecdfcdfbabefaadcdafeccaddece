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
    @CrossOrigin(origins = "http://localhost:8081")
    public ApiResponse registerUser(@RequestBody User user) {
        if (userService.registerUser(user)) {
            return new ApiResponse("success");
        } else {
            return new ApiResponse("Failed to register user");
        }
    }

    @PostMapping("/auth/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<User> handler2(@RequestBody User user) throws Exception {

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

            // Include the full user object in the response
            // loggedInUser.setPassword(null); // Exclude password for security
            loggedInUser.setToken(token);

            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("User not found with email: " + user.getEmail());
        }
    }

}