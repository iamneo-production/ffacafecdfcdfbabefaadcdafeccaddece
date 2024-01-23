package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Movie;
import com.examly.springapp.model.User;
import com.examly.springapp.respository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    public String passwordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public boolean registerUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        this.userRepo.save(user);
        return true;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> users = userRepo.findByEmail(email);

        if (users.isEmpty()) {
            // throw new UsernameNotFoundException("User not found with email: " + email);
            return null;
        }
        User user = users.get(0);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public List<User> findAllUsers() {
        return (List<User>) this.userRepo.findAll();
    }

    public boolean updateUser(User user, Long id) {
        if (userRepo.existsById(id)) {
            return userRepo.save(user) != null ? true : false;
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> getAllUser() {

        List<User> userList = userRepo.findAll();
        return userList;
    }

    public User getUserById(Long id) {

        if (userRepo.existsById(id)) {
            User user = userRepo.findById(id).get();
            return user;
        }

        return null;
    }

    public User getUserByEmail(String email) {
        List<User> users = userRepo.findByEmail(email);

        return users.isEmpty() ? null : users.get(0);
    }

}
