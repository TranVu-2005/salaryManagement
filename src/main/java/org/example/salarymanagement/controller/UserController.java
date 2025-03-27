package org.example.salarymanagement.controller;

import org.example.salarymanagement.model.User;
import org.example.salarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Display the form and list of users
    @GetMapping("/")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    // Add or Update a user
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, Model model) {
        String message = userService.saveUser(user);
        model.addAttribute("message", message);
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    // Edit a user
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id).orElse(new User());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    // Delete a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("message", "User deleted successfully");
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @PostMapping("/search")
    public String searchUsers(@RequestParam String name, Model model) {
        List<User> users = userService.searchUsersByName(name);
        model.addAttribute("user", new User());
        model.addAttribute("users", users);
        return "index";
    }
}