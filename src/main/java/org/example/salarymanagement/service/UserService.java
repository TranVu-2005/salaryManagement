package org.example.salarymanagement.service;

import org.example.salarymanagement.model.User;
import org.example.salarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create or Update a user
    public String saveUser(User user) {
        Optional<User> existingUser = userRepository.findByName(user.getName());
        if (existingUser.isPresent() && (user.getId() == null || !user.getId().equals(existingUser.get().getId()))) {
            return "Error while creating User: Unable to create. A User with name " + user.getName() + " already exist.";
        }
        userRepository.save(user);
        return user.getId() == null ? "User created successfully" : "User updated successfully";
    }

    // Read all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> searchUsersByName(String name) {
        return userRepository.findAll().stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}