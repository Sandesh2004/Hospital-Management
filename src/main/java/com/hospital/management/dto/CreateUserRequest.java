package com.hospital.management.dto;

import com.hospital.management.model.Availability;

import java.util.List;

public class CreateUserRequest {

    private String username;
    private String password;
    private String role;

    // Doctor-specific fields
    private String name;
    private String email;
    private String specialization;
    private int experience;
    private String phone;
    private int patientsPerHour;

    private List<Availability> availability;

    // getters & setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPatientsPerHour() {
        return patientsPerHour;
    }

    public void setPatientsPerHour(int patientsPerHour) {
        this.patientsPerHour = patientsPerHour;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }
}