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

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock
    AppointmentRepository appointmentRepository;

    @Test
    void findAllAppointment(){
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment(1L, "test appointment", "두정", "test1", 3, null, null));

        given(appointmentRepository.findAll())
                .willReturn(appointmentList);

        AppointmentService service = new AppointmentService(appointmentRepository); // 서비스 로직의 테스트가 이루어짐...

        List<String> appointmentCodeList = new ArrayList<>();
        appointmentCodeList.add("test1");

        List<Appointment> findAppointmentList = service.findAllAppointment(appointmentCodeList);
        Assertions.assertThat(findAppointmentList.size()).isEqualTo(1);
        Assertions.assertThat(findAppointmentList.get(0).getCode()).isEqualTo("test1");
    }
}