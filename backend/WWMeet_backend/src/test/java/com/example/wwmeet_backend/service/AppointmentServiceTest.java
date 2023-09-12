package com.example.wwmeet_backend.service;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.repository.AppointmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock
    AppointmentRepository appointmentRepository;

    @Test
    void findAllAppointment(){
        Appointment appointment = new Appointment(1L, "test appointment", "두정", "test1", 3, null);

        given(appointmentRepository.findByAppointmentCode(anyString()))
                .willReturn(Optional.of(appointment));

        AppointmentService service = new AppointmentService(appointmentRepository); // 서비스 로직의 테스트가 이루어짐...

        List<String> appointmentCodeList = new ArrayList<>();
        appointmentCodeList.add("test1");

        List<Appointment> findAppointmentList = service.findAllAppointment(appointmentCodeList);
        Assertions.assertThat(findAppointmentList.size()).isEqualTo(1);
        Assertions.assertThat(findAppointmentList.get(0).getIdentificationCode()).isEqualTo("test1");
    }

    @Test
    void findAppointmentById() throws Exception{
        given(appointmentRepository.findById(anyLong()))
                .willReturn(Optional.of(new Appointment(1L, "test", "test1", "test1", 2, null)));
        AppointmentService service = new AppointmentService(appointmentRepository);
        Long findId = 1L;

        Appointment findAppointment = service.findAppointmentById(findId);

        Assertions.assertThat(findAppointment.getId()).isEqualTo(1L);
        Assertions.assertThat(findAppointment.getAppointmentName()).isEqualTo("test");
    }

    @Test
    void saveAppointment() throws Exception{
        given(appointmentRepository.save(any()))
                .willReturn(new Appointment(1L, "test", "test", "test1", 2, null));

        AppointmentService service = new AppointmentService(appointmentRepository);
        Appointment appointment = new Appointment(1L, "test", "test", "test1", 2, null);
        Appointment savedAppointment = service.saveAppointment(appointment);

        Assertions.assertThat(savedAppointment.getAppointmentName()).isEqualTo(appointment.getAppointmentName());
    }
}