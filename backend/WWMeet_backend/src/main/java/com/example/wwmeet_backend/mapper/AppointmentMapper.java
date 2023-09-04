package com.example.wwmeet_backend.mapper;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.dto.AppointmentFindDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel =  "spring")
public interface AppointmentMapper {
    AppointmentFindDto toFindDto(Appointment appointment);
    Appointment FindDtoToUser(AppointmentFindDto appointmentFindDto);
}
