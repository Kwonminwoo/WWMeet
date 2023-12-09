package com.example.wwmeet_backend.participant.repository;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.participant.domain.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("select p from Participant p where p.appointment = :appointment and p.participantName = :participantName")
    Optional<Participant> findByParticipantName(@Param("appointment") Appointment appointment, @Param("participantName") String participantName);

    List<Participant> findByAppointmentId(Long id);
}
