package com.example.wwmeet_backend.appointmentDate.repository;

import com.example.wwmeet_backend.appointmentDate.domain.AppointmentDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDateRepository extends JpaRepository<AppointmentDate, Long> {

}
