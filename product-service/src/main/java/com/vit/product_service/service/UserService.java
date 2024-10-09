package com.vit.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vit.product_service.dto.CreateUserRequest;
import com.vit.product_service.model.User;
import com.vit.product_service.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserRequest createUserRequest) {
        userRepository.save(User.builder()
                .name(createUserRequest.getName())
                .phone(createUserRequest.getPhone())
                .password(createUserRequest.getPassword())
                .build());
        log.info("User {} is saved", createUserRequest.getName());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id.toString()).orElse(null);
    }
    
    public void deleteUserById(Long id) {
        userRepository.deleteById(id.toString());
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
}
