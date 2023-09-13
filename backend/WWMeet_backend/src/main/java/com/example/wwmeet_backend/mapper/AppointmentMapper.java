package com.example.wwmeet_backend.mapper;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.dto.AppointmentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface AppointmentMapper {
    AppointmentResponseDto toResponseDto(Appointment appointment);
    Appointment responseDtoTo(AppointmentResponseDto appointmentFindDto);
}