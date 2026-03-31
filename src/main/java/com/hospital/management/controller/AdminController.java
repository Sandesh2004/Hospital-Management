package com.hospital.management.controller;

import com.hospital.management.dto.CreateUserRequest;
import com.hospital.management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public String test() {
        return "Admin API working";
    }
}