package com.example.wwmeet_backend.mapper;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Vote;
import com.example.wwmeet_backend.dto.AppointmentFindDto;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-02T22:11:07+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.18 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public AppointmentFindDto toFindDto(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        Long id = null;
        String appointmentName = null;
        String appointmentPlace = null;
        String appointmentCode = null;
        int peopleNum = 0;
        LocalDateTime appointmentDate = null;

        id = appointment.getId();
        appointmentName = appointment.getAppointmentName();
        appointmentPlace = appointment.getAppointmentPlace();
        appointmentCode = appointment.getAppointmentCode();
        peopleNum = appointment.getPeopleNum();
        appointmentDate = appointment.getAppointmentDate();

        AppointmentFindDto appointmentFindDto = new AppointmentFindDto( id, appointmentName, appointmentPlace, appointmentCode, peopleNum, appointmentDate );

        return appointmentFindDto;
    }

    @Override
    public Appointment FindToUser(AppointmentFindDto appointmentFindDto) {
        if ( appointmentFindDto == null ) {
            return null;
        }

        String appointmentName = null;
        String appointmentPlace = null;
        String appointmentCode = null;
        int peopleNum = 0;
        LocalDateTime appointmentDate = null;

        appointmentName = appointmentFindDto.getAppointmentName();
        appointmentPlace = appointmentFindDto.getAppointmentPlace();
        appointmentCode = appointmentFindDto.getAppointmentCode();
        peopleNum = appointmentFindDto.getPeopleNum();
        appointmentDate = appointmentFindDto.getAppointmentDate();

        Long id = null;
        List<Vote> voteList = null;

        Appointment appointment = new Appointment( id, appointmentName, appointmentPlace, appointmentCode, peopleNum, appointmentDate, voteList );

        return appointment;
    }
}
