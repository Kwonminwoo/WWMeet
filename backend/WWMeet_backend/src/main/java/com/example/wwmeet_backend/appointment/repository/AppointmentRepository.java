package com.example.wwmeet_backend.appointment.repository;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment a where a.identificationCode = :identificationCode")
    Optional<Appointment> findByIdentificationCode(@Param("identificationCode") String identificationCode);
}