package com.example.wwmeet_backend.domain.appointment.repository;

import com.example.wwmeet_backend.domain.appointment.domain.Appointment;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentListResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.identificationCode = :identificationCode")
    Optional<Appointment> findByIdentificationCode(
        @Param("identificationCode") String identificationCode);

    @Query("select a from Appointment a where a.member.id = :memberId")
    List<Appointment> findAppointmentByMemberId(@Param("memberId") Long memberId);
}