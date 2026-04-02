package com.hospital.management.repository;

import com.hospital.management.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    List<Appointment> findByDoctorIdAndDateOrderByQueueNumberDesc(String doctorId, LocalDate date);

    long countByDoctorIdAndDate(String doctorId, LocalDate date);

    List<Appointment> findByDoctorIdAndDate(String doctorId, LocalDate date);

    List<Appointment> findByDoctorIdAndDateAndPatientNameContainingIgnoreCase(
            String doctorId,
            LocalDate date,
            String name
    );

    List<Appointment> findByPatientId(String patientId);

    List<Appointment> findByStatus(String status);
}