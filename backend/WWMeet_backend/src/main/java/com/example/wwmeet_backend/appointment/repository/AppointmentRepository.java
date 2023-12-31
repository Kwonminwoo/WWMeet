package com.example.wwmeet_backend.appointment.repository;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment a where a.identificationCode = :identificationCode")
    Optional<Appointment> findByIdentificationCode(@Param("identificationCode") String identificationCode);
}