package com.hospital.management.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/test")
    public String test() {
        return "Public API working";
    }
}