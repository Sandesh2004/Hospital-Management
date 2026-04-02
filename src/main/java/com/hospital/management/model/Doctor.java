package com.hospital.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "doctors")
public class Doctor {

    @Id
    private String id;

    private String name;
    private String specialization;
    private String email;

    private String userId; // 🔥 link to User._id

    private int experience;
    private String phone;

    private int patientsPerHour;

    private List<Availability> availability;

    // getters & setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getPatientsPerHour() { return patientsPerHour; }
    public void setPatientsPerHour(int patientsPerHour) { this.patientsPerHour = patientsPerHour; }

    public List<Availability> getAvailability() { return availability; }
    public void setAvailability(List<Availability> availability) { this.availability = availability; }
}