package com.hospital.management.service;

import com.hospital.management.exception.BadRequestException;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.Doctor;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Appointment bookAppointment(Appointment appointment) {

        // 1️⃣ Check doctor exists
        Doctor doctor = doctorRepository.findById(appointment.getDoctorId())
                .orElseThrow(() -> new BadRequestException("Doctor not found"));

        // 2️⃣ Check availability (day)
        DayOfWeek day = appointment.getDate().getDayOfWeek();

        var availability = doctor.getAvailability().stream()
                .filter(a -> a.getDay().equalsIgnoreCase(day.toString()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Doctor not available on this day"));

        // 3️⃣ Calculate hours
        int startHour = Integer.parseInt(availability.getStartTime().split(":")[0]);
        int endHour = Integer.parseInt(availability.getEndTime().split(":")[0]);

        int totalHours = endHour - startHour;

        // 4️⃣ Calculate max capacity
        int maxPatients = totalHours * doctor.getPatientsPerHour();

        // 5️⃣ Get current bookings count
        long bookedCount = appointmentRepository.countByDoctorIdAndDate(
                appointment.getDoctorId(),
                appointment.getDate()
        );

        if (bookedCount >= maxPatients) {
            throw new BadRequestException("Doctor is fully booked for this day");
        }

        // 6️⃣ Assign queue
        int queueNumber = (int) bookedCount + 1;

        appointment.setPatientName(appointment.getPatientName());
        appointment.setDoctorName(doctor.getName());
        appointment.setQueueNumber(queueNumber);
        appointment.setStatus("BOOKED");

        return appointmentRepository.save(appointment);
    }

    public int getRemainingSlots(String doctorId, String dateStr) {

        LocalDate date = LocalDate.parse(dateStr);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new BadRequestException("Doctor not found"));

        DayOfWeek day = date.getDayOfWeek();

        var availability = doctor.getAvailability().stream()
                .filter(a -> a.getDay().equalsIgnoreCase(day.toString()))
                .findFirst()
                .orElse(null);

        if (availability == null) {
            return 0;
        }

        int startHour = Integer.parseInt(availability.getStartTime().split(":")[0]);
        int endHour = Integer.parseInt(availability.getEndTime().split(":")[0]);

        int totalHours = endHour - startHour;

        int maxCapacity = totalHours * doctor.getPatientsPerHour();

        long booked = appointmentRepository.countByDoctorIdAndDate(doctorId, date);

        return (int) (maxCapacity - booked);
    }

    public List<Appointment> getDoctorAppointments(String doctorId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);

        return appointmentRepository.findByDoctorIdAndDate(doctorId, date);
    }

    public List<Appointment> searchAppointments(String doctorId, String dateStr, String name) {
        LocalDate date = LocalDate.parse(dateStr);

        return appointmentRepository
                .findByDoctorIdAndDate(doctorId, date)
                .stream()
                .filter(a -> a.getPatientName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public Appointment updateStatus(String id, String status) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);

        return appointmentRepository.save(appointment);
    }

    // Get appointment by ID
    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    // Get patient's appointments
    public List<Appointment> getPatientAppointments(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Cancel appointment
    public Appointment cancelAppointment(String id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus("CANCELLED");
        return appointmentRepository.save(appointment);
    }

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        
        // Populate doctor names
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId() != null) {
                Doctor doctor = doctorRepository.findById(appointment.getDoctorId()).orElse(null);
                if (doctor != null) {
                    appointment.setDoctorName(doctor.getName());
                }
            }
        }
        
        return appointments;
    }
}