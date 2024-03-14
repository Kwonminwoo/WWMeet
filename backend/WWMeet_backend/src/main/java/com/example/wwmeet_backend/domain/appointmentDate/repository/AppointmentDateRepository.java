package com.example.wwmeet_backend.domain.appointmentDate.repository;

import com.example.wwmeet_backend.domain.appointmentDate.domain.AppointmentDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDateRepository extends JpaRepository<AppointmentDate, Long> {

    List<AppointmentDate> findByAppointmentId(Long id);
}
