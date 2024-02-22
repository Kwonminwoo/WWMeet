package com.example.wwmeet_backend.possibleschedule.repository;

import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossibleScheduleRepository extends JpaRepository<PossibleSchedule, Long> {
    List<PossibleSchedule> findAllByAppointmentId(Long id);
}
