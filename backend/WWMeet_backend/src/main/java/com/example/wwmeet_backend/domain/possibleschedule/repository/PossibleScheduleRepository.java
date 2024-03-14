package com.example.wwmeet_backend.domain.possibleschedule.repository;

import com.example.wwmeet_backend.domain.possibleschedule.domain.PossibleSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossibleScheduleRepository extends JpaRepository<PossibleSchedule, Long> {

    List<PossibleSchedule> findAllByAppointmentId(Long id);
}
