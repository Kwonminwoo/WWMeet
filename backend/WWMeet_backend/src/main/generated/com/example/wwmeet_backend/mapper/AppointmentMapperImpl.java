package com.example.wwmeet_backend.mapper;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.dto.AppointmentResponseDto;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-11T23:11:49+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.18 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public AppointmentResponseDto toResponseDto(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        Long id = null;
        String appointmentName = null;
        String appointmentPlace = null;
        int peopleNum = 0;

        id = appointment.getId();
        appointmentName = appointment.getAppointmentName();
        appointmentPlace = appointment.getAppointmentPlace();
        peopleNum = appointment.getPeopleNum();

        String appointmentCode = null;
        LocalDateTime appointmentDate = null;

        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto( id, appointmentName, appointmentPlace, appointmentCode, peopleNum, appointmentDate );

        return appointmentResponseDto;
    }

    @Override
    public Appointment ResponseDtoTo(AppointmentResponseDto appointmentFindDto) {
        if ( appointmentFindDto == null ) {
            return null;
        }

        String appointmentName = null;
        String appointmentPlace = null;
        int peopleNum = 0;
        Long id = null;

        appointmentName = appointmentFindDto.getAppointmentName();
        appointmentPlace = appointmentFindDto.getAppointmentPlace();
        peopleNum = appointmentFindDto.getPeopleNum();
        id = appointmentFindDto.getId();

        String identificationCode = null;
        LocalDateTime voteDeadline = null;

        Appointment appointment = new Appointment( id, appointmentName, appointmentPlace, identificationCode, peopleNum, voteDeadline );

        return appointment;
    }
}
