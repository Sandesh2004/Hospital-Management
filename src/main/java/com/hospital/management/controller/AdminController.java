package com.hospital.management.controller;

import com.hospital.management.dto.CreateUserRequest;
import com.hospital.management.model.User;
import com.hospital.management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-user")
    public String createUser(@RequestBody CreateUserRequest request) {

        adminService.createUser(request);

        return "User created successfully";
    }

    // Get all users
    @GetMapping("/users")
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminService.getAllUsers(PageRequest.of(page, size));
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return adminService.getUserById(id);
    }

    // Get user by username
    @GetMapping("/users/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return adminService.getUserByUsername(username);
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);
        return "User deleted successfully";
    }

    // Update user role
    @PutMapping("/users/{id}/role")
    public User updateUserRole(@PathVariable String id, @RequestParam String role) {
        return adminService.updateUserRole(id, role);
    }

    @GetMapping("/test")
    public String test() {
        return "Admin API working";
    }
}