package com.example.kafka.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.kafka.model.User;
import com.example.kafka.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Create a new user
    public User createUser(String name, String email, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    // Get a user by ID
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(Long id, String name, String email, String password){
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            userRepository.save(user);
            return user;
        } else {
            return null;
        }
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}