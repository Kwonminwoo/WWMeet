package com.example.wwmeet_backend.domain.participant.repository;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.participant.entity.Participant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("select p from Participant p where p.appointment = :appointment and p.participantName = :participantName")
    Optional<Participant> findByParticipantName(@Param("appointment") Appointment appointment,
        @Param("participantName") String participantName);

    List<Participant> findByAppointmentId(Long id);

    Optional<Participant> findByMemberIdAndAppointment(Long memberId, Appointment appointment);

    List<Participant> findAllByMemberId(Long memberId);
}
